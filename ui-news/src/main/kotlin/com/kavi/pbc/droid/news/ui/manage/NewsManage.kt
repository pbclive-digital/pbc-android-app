package com.kavi.pbc.droid.news.ui.manage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kavi.droid.color.palette.extension.shadow
import com.kavi.pbc.droid.data.dto.news.NewsStatus
import com.kavi.pbc.droid.lib.common.ui.component.AppButtonWithIcon
import com.kavi.pbc.droid.lib.common.ui.component.Title
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import com.kavi.pbc.droid.news.R
import com.kavi.pbc.droid.news.data.model.NewsManageMode
import com.kavi.pbc.droid.news.ui.common.NewsItemForAdmin
import com.kavi.pbc.droid.news.ui.manage.dialog.DeleteConfirmationDialog
import com.kavi.pbc.droid.news.ui.manage.dialog.PublishConfirmationDialog
import javax.inject.Inject
import kotlin.Boolean

class NewsManage @Inject constructor() {

    @Composable
    fun NewsManageUI(navController: NavController, viewModel: NewsManageViewModel = hiltViewModel()) {
        val context = LocalContext.current

        val showDeleteConfirmationDialog = remember { mutableStateOf(false) }
        val deletingNewsId = remember { mutableStateOf("") }
        val newsMode = remember { mutableStateOf(NewsManageMode.UNSELECTED) }
        val showPublishConfirmationDialog = remember { mutableStateOf(false) }
        val publishingNewsId = remember { mutableStateOf("") }

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
                        titleText = stringResource(R.string.label_news_manage),
                    )

                    Column (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 30.dp)
                            .verticalScroll(state = rememberScrollState())
                    ) {
                        Text(
                            text = stringResource(R.string.phrase_news_manage),
                            fontFamily = PBCFontFamily,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Justify,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        AppButtonWithIcon(
                            modifier = Modifier.padding(top = 12.dp),
                            label = stringResource(R.string.label_news_create),
                            icon = painterResource(R.drawable.icon_news)
                        ) {
                            //navController.navigate("event/event-create")
                        }

                        DraftedNews(
                            navController = navController,
                            publishConfirmation = showPublishConfirmationDialog,
                            publishingId = publishingNewsId,
                            deleteConfirmation = showDeleteConfirmationDialog,
                            deletingId = deletingNewsId,
                            newsMode = newsMode
                        )

                        ActiveNews(
                            navController = navController,
                            deleteConfirmation = showDeleteConfirmationDialog,
                            deletingId = deletingNewsId,
                            newsMode = newsMode
                        )
                    }
                }
            }
        }

        DeleteConfirmationDialog(
            showDialog = showDeleteConfirmationDialog,
            onAgree = {
                showDeleteConfirmationDialog.value = false
                viewModel.deleteNews(newsId = deletingNewsId.value, newsMode = newsMode.value)
                deletingNewsId.value = ""
                newsMode.value = NewsManageMode.UNSELECTED
            },
            onDisagree = {
                showDeleteConfirmationDialog.value = false
                deletingNewsId.value = ""
                newsMode.value = NewsManageMode.UNSELECTED
            }
        )

        PublishConfirmationDialog(
            showDialog = showPublishConfirmationDialog,
            onAgree = {
                showPublishConfirmationDialog.value = false
                viewModel.publishDraftNews(newsId = publishingNewsId.value)
                publishingNewsId.value = ""
            },
            onDisagree = {
                showPublishConfirmationDialog.value = false
                publishingNewsId.value = ""
            }
        )
    }

    @Composable
    fun DraftedNews(navController: NavController, viewModel: NewsManageViewModel = hiltViewModel(),
                    publishConfirmation: MutableState<Boolean>,
                    publishingId: MutableState<String>,
                    deleteConfirmation: MutableState<Boolean>,
                    deletingId: MutableState<String>,
                    newsMode: MutableState<NewsManageMode>) {

        val draftNewsList by viewModel.draftNewsList.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.fetchDraftNewsList()
        }

        Text(
            text = stringResource(R.string.label_news_draft),
            fontFamily = PBCFontFamily,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
        )

        Column {
            if (draftNewsList.isNotEmpty()) {
                draftNewsList.forEachIndexed { index, news ->
                    NewsItemForAdmin(news = news, isDraftNews = true, onModify = {
                        // Navigate to edit screen
                        //val tempEventKey = eventLocalDataSource.setModifyingEvent(event = event)
                        //navController.navigate("event/event-edit/$tempEventKey")
                    }, onPublish = {
                        publishConfirmation.value = true
                        publishingId.value = news.id
                    }, onDelete = {
                        deleteConfirmation.value = true
                        newsMode.value = NewsManageMode.DRAFT
                        deletingId.value = news.id
                    })
                    if (index < draftNewsList.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.shadow
                        )
                    }
                }
            } else {
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.label_news_no_draft),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }

    @Composable
    fun ActiveNews(navController: NavController, viewModel: NewsManageViewModel = hiltViewModel(),
                   deleteConfirmation: MutableState<Boolean>,
                   deletingId: MutableState<String>,
                   newsMode: MutableState<NewsManageMode>) {

        val activeNewsList by viewModel.activeNewsList.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.fetchActiveNewsList()
        }

        Text(
            text = stringResource(R.string.label_news_active),
            fontFamily = PBCFontFamily,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
        )

        Column {
            if (activeNewsList.isNotEmpty()) {
                activeNewsList.forEachIndexed { index, news ->
                    NewsItemForAdmin(news = news, isDraftNews = false, onModify = {
                        // Navigate to edit screen
                        //val tempEventKey = eventLocalDataSource.setModifyingEvent(event = event)
                        //navController.navigate("event/event-edit/$tempEventKey")
                    }, onPublish = {
                        /* Nothing to implement */
                    }, onDelete = {
                        deleteConfirmation.value = true
                        newsMode.value = NewsManageMode.ACTIVE
                        deletingId.value = news.id
                    })
                    if (index < activeNewsList.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.shadow
                        )
                    }
                }
            } else {
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.label_news_no_active),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}