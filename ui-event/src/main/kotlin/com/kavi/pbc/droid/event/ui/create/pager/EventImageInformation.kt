package com.kavi.pbc.droid.event.ui.create.pager

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.kavi.pbc.droid.event.R
import com.kavi.pbc.droid.event.ui.create.EventCreateViewModel
import com.kavi.pbc.droid.event.util.FilePickerUtil
import com.kavi.pbc.droid.event.util.OpenFileResult
import com.kavi.pbc.droid.lib.common.ui.component.AppFilledButton
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import javax.inject.Inject

class EventImageInformation @Inject constructor() {
    @Composable
    fun EventImageUI(viewModel: EventCreateViewModel = hiltViewModel()) {
        val context = LocalContext.current
        val activity = context as? Activity
        var imageUri by remember { mutableStateOf(viewModel.eventImageUri.value) }

        // Image picker launcher
        val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                uri?.let {
                    imageUri = it
                    viewModel.updateEventImageUrl(it)

                    when(val result = FilePickerUtil.handleOpenDocument(activity, imageUri)) {
                        OpenFileResult.DifferentResult, OpenFileResult.OpenFileWasCancelled, OpenFileResult.ErrorOpeningFile -> {

                        }
                        is OpenFileResult.FileWasOpened -> {
                            viewModel.updateEventImageFile(result.file)
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

        Box (
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(start = 4.dp, end = 4.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.label_event_image),
                    fontFamily = PBCFontFamily,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                HorizontalDivider(
                    modifier = Modifier.padding(2.dp),
                    thickness = 2.dp
                )

                Text(
                    text = stringResource(R.string.phrase_event_image),
                    fontFamily = PBCFontFamily,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                AppFilledButton(
                    modifier = Modifier.padding(top = 12.dp),
                    label = stringResource(R.string.label_pick_image).uppercase()
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
                                .padding(top = 16.dp)
                                .size(300.dp)
                        )
                    }
                }?: run {
                    viewModel.newEvent.value.eventImage?.let {
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
                                    .size(300.dp)
                                    .padding(5.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}