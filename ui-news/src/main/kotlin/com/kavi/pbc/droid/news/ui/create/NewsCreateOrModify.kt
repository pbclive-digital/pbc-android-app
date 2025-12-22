package com.kavi.pbc.droid.news.ui.create

import android.Manifest
import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.kavi.pbc.droid.lib.common.ui.component.AppFilledButton
import com.kavi.pbc.droid.lib.common.ui.component.AppFullScreenLoader
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineMultiLineTextField
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineTextField
import com.kavi.pbc.droid.lib.common.ui.component.Title
import com.kavi.pbc.droid.lib.parent.util.FilePickerUtil
import com.kavi.pbc.droid.lib.parent.util.OpenFileResult
import com.kavi.pbc.droid.news.R
import com.kavi.pbc.droid.news.data.model.NewsCreationStatus
import javax.inject.Inject

class NewsCreateOrModify @Inject constructor() {

    @Composable
    fun NewsCreateOrModifyUI(navController: NavController,
                             modifyingNewsKey: String? = null,
                             viewModel: NewsCreateOrModifyViewModel = hiltViewModel()) {

        val context = LocalContext.current
        val activity = context as? Activity
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
        var imageUri by remember { mutableStateOf(viewModel.newsImageUri.value) }

        val newsCreateStatus by viewModel.newsCreationStatus.collectAsState()

        // Image picker launcher
        val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                uri?.let {
                    imageUri = it
                    viewModel.updateNewsImageUrl(it)

                    when(val result = FilePickerUtil.handleOpenDocument(activity, imageUri)) {
                        OpenFileResult.DifferentResult, OpenFileResult.OpenFileWasCancelled, OpenFileResult.ErrorOpeningFile -> {
                            // Nothing to do here
                        }
                        is OpenFileResult.FileWasOpened -> {
                            viewModel.updateNewsImageFile(result.file)
                        }
                    }
                }
            }
        )

        // Permission launcher
        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                if (isGranted) {
                    // Once permission is granted, open gallery
                    galleryLauncher.launch("image/*")
                } else {
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        )

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
                                .height(250.dp),
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

                        AppFilledButton(
                            modifier = Modifier.padding(top = 12.dp),
                            label = stringResource(R.string.label_news_pick_image).uppercase()
                        ) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                            } else {
                                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            }
                        }

                        imageUri?.let {
                            Box (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(it),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .size(250.dp)
                                )
                            }
                        }?: run {
                            viewModel.news.value.newsImage?.let {
                                Box (
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    AsyncImage(
                                        model = it,
                                        contentDescription = "Profile Picture",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(250.dp)
                                            .padding(5.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        AppFilledButton(
                            label = if (isModify) stringResource(R.string.label_news_modify)
                            else stringResource(R.string.label_news_create),
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            viewModel.uploadNewsImageAndCreateOrModifyNews(isModify = isModify)
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
                    Toast.makeText(context, stringResource(R.string.label_news_create_failed), Toast.LENGTH_LONG).show()
                }
                NewsCreationStatus.SUCCESS -> {
                    navController.popBackStack()
                }
            }
        }
    }
}