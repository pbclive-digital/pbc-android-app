package com.kavi.pbc.droid.news.ui.list

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kavi.pbc.droid.data.dto.news.News
import com.kavi.pbc.droid.lib.common.ui.component.AppFullScreenLoader
import com.kavi.pbc.droid.lib.common.ui.component.Title
import com.kavi.pbc.droid.lib.common.ui.component.news.NewsItem
import com.kavi.pbc.droid.news.R
import com.kavi.pbc.droid.news.data.model.NewsListFetchStatus
import com.kavi.pbc.droid.news.ui.selected.NewsSelected
import javax.inject.Inject

class ActiveNewsList @Inject constructor() {

    @Inject
    lateinit var newsSelected: NewsSelected

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ActiveNewsListUI(navController: NavController, viewModel: ActiveNewsListViewModel = hiltViewModel()) {

        val context = LocalContext.current
        val activeNewsFetchStatus by viewModel.activeNewsFetchStatus.collectAsState()
        val activeNewsList by viewModel.activeNewsList.collectAsState()

        val selectedNewsSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
        val showNewsSheet = remember { mutableStateOf(false) }

        var selectedNews by remember { mutableStateOf(News()) }

        LaunchedEffect(Unit) {
            viewModel.fetchActiveNewsList()
        }

        Box {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(top = 56.dp, start = 16.dp, end = 16.dp)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Title(
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                        titleText = stringResource(R.string.label_news),
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 16.dp, bottom = 30.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        itemsIndexed(activeNewsList) {index, news ->
                            NewsItem(news = news) {
                                showNewsSheet.value = true
                                selectedNews = news
                            }
                            if (index < activeNewsList.lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.fillMaxWidth(),
                                    thickness = 1.dp,
                                    color = Color.LightGray
                                )
                            }
                        }
                    }
                }
            }

            when(activeNewsFetchStatus) {
                NewsListFetchStatus.NONE -> {}
                NewsListFetchStatus.PENDING -> {
                    AppFullScreenLoader()
                }
                NewsListFetchStatus.FAILURE -> {
                    Toast.makeText(context, stringResource(R.string.label_news_active_fetch_failure), Toast.LENGTH_LONG).show()
                    EmptyNewsList()
                }
                NewsListFetchStatus.EMPTY -> {
                    EmptyNewsList()
                }
                NewsListFetchStatus.SUCCESS -> {
                    // When it success, items will reload with state.
                }
            }

            if (showNewsSheet.value) {
                newsSelected.NewsSelectedBottomSheetUI(
                    sheetState = selectedNewsSheetState,
                    showSheet = showNewsSheet,
                    selectedNews = selectedNews
                )
            }
        }
    }

    @Composable
    fun EmptyNewsList() {
        Box (
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(top = 130.dp, start = 16.dp, end = 16.dp, bottom = 30.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.label_news_active_fetch_empty),
                textAlign = TextAlign.Center,
            )
        }
    }
}