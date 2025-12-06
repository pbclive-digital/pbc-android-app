package com.kavi.pbc.droid.news.ui.create

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kavi.pbc.droid.lib.common.ui.component.AppFilledButton
import com.kavi.pbc.droid.lib.common.ui.component.AppFullScreenLoader
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineMultiLineTextField
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineTextField
import com.kavi.pbc.droid.lib.common.ui.component.Title
import com.kavi.pbc.droid.news.R
import com.kavi.pbc.droid.news.data.model.NewsCreationStatus
import javax.inject.Inject

class NewsCreateOrModify @Inject constructor() {

    @Composable
    fun NewsCreateOrModifyUI(navController: NavController,
                             modifyingNewsKey: String? = null,
                             viewModel: NewsCreateOrModifyViewModel = hiltViewModel()) {

        val context = LocalContext.current
        var isModify by remember { mutableStateOf(false) }

        modifyingNewsKey?.let {
            isModify = true
            viewModel.setModifyingNews(newsKey = it)
        }

        val newsHeadline = remember { mutableStateOf(TextFieldValue(viewModel.news.value.title)) }
        val newsContent = remember { mutableStateOf(TextFieldValue(viewModel.news.value.content)) }

        val facebookLink = remember { mutableStateOf(TextFieldValue(
            viewModel.news.value.facebookLink ?: run { "" }
        )) }

        val newsCreateStatus by viewModel.newsCreationStatus.collectAsState()

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
                        titleText = stringResource(R.string.label_news_create),
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 30.dp)
                            .verticalScroll(state = rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AppOutlineTextField (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            headingText = stringResource(R.string.label_news_headline).uppercase(),
                            contentText = newsHeadline,
                            onValueChange = { newValue ->
                                newsHeadline.value = newValue
                                viewModel.updateNewsHeadline(newsHeadline.value.text)
                            }
                        )

                        AppOutlineMultiLineTextField (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp)
                                .height(300.dp),
                            headingText = stringResource(R.string.label_news_content).uppercase(),
                            contentText = newsContent,
                            maxLines = 20,
                            onValueChange = { newValue ->
                                newsContent.value = newValue
                                viewModel.updateNewsContent(newsContent.value.text)
                            }
                        )

                        AppOutlineTextField (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            headingText = stringResource(R.string.label_news_link).uppercase(),
                            contentText = facebookLink,
                            onValueChange = { newValue ->
                                facebookLink.value = newValue
                                viewModel.updateNewsLink(facebookLink.value.text)
                            }
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        AppFilledButton(
                            label = if (isModify) stringResource(R.string.label_news_modify)
                            else stringResource(R.string.label_news_create),
                            modifier = Modifier.padding(top = 12.dp)
                        ) {
                            if (isModify) {
                                viewModel.updateNews()
                            } else {
                                viewModel.createNews()
                            }
                        }
                    }
                }
            }

            when(newsCreateStatus) {
                NewsCreationStatus.NONE -> {}
                NewsCreationStatus.PENDING -> {
                    AppFullScreenLoader()
                }
                NewsCreationStatus.FAILURE -> {
                    Toast.makeText(context, stringResource(R.string.label_news_create_create_failed), Toast.LENGTH_LONG).show()
                }
                NewsCreationStatus.SUCCESS -> {
                    navController.popBackStack()
                }
            }
        }
    }
}