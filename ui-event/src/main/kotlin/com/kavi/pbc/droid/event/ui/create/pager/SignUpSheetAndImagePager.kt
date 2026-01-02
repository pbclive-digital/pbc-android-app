package com.kavi.pbc.droid.event.ui.create.pager

import android.Manifest
import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.kavi.droid.color.palette.extension.shadow
import com.kavi.pbc.droid.data.dto.event.signup.SignUpSheet
import com.kavi.pbc.droid.event.R
import com.kavi.pbc.droid.event.ui.create.EventCreateViewModel
import com.kavi.pbc.droid.event.ui.create.dialog.SignUpSheetCreateDialog
import com.kavi.pbc.droid.lib.common.ui.component.AppButtonWithIcon
import com.kavi.pbc.droid.lib.common.ui.component.AppFilledButton
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import com.kavi.pbc.droid.lib.parent.util.FilePickerUtil
import com.kavi.pbc.droid.lib.parent.util.OpenFileResult
import javax.inject.Inject

class SignUpSheetAndImagePager @Inject constructor() {
    @Composable
    fun SignUpSheetAndImageUI(viewModel: EventCreateViewModel = hiltViewModel()) {
        val context = LocalContext.current
        val activity = context as? Activity

        var isAdditionalSignUpsChecked by remember { mutableStateOf(viewModel.newEvent.value.signUpSheetAvailable) }
        val showCreateSignUpSheetItemDialog = remember { mutableStateOf(false) }

        var imageUri by remember { mutableStateOf(viewModel.eventImageUri.value) }

        val signUpSheetItemList by viewModel.signUpSheetItemList.collectAsState()

        // Image picker launcher
        val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                uri?.let {
                    imageUri = it
                    viewModel.updateEventImageUrl(it)

                    when(val result = FilePickerUtil.handleOpenDocument(activity, imageUri)) {
                        OpenFileResult.DifferentResult, OpenFileResult.OpenFileWasCancelled, OpenFileResult.ErrorOpeningFile -> {
                            // Nothing to do here
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
                    text = stringResource(R.string.label_additional_sign_up),
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
                    text = stringResource(R.string.phrase_additional_sign_up),
                    fontFamily = PBCFontFamily,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.label_is_additional_sign_up_sheets),
                        fontFamily = PBCFontFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Checkbox(
                        checked = isAdditionalSignUpsChecked,
                        onCheckedChange = { newCheckedState ->
                            isAdditionalSignUpsChecked = newCheckedState
                            viewModel.updateSignUpAvailabilityFlag(newCheckedState)
                        }
                    )
                }

                if (isAdditionalSignUpsChecked) {
                    AppButtonWithIcon (
                        modifier = Modifier.padding(top = 8.dp),
                        label = stringResource(R.string.label_add_new_sign_up_sheet),
                        icon = painterResource(R.drawable.icon_plus)
                    ) {
                        showCreateSignUpSheetItemDialog.value = true
                    }

                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                            .clip(shape = RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        signUpSheetItemList.forEachIndexed { index, signUpSheet ->
                            key (signUpSheet.sheetId) {
                                SignUpSheetListItem(
                                    signUpSheet = signUpSheet,
                                    onDelete = {
                                        viewModel.removeSignUpSheet(signUpSheet)
                                    }
                                )
                                if (index < signUpSheetItemList.lastIndex) {
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 16.dp, end = 8.dp),
                                        thickness = 1.dp,
                                        color = MaterialTheme.colorScheme.shadow
                                    )
                                }
                            }
                        }
                    }
                }

                Text(
                    text = stringResource(R.string.label_event_image),
                    fontFamily = PBCFontFamily,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(top = 20.dp)
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

                SignUpSheetCreateDialog(
                    showDialog = showCreateSignUpSheetItemDialog,
                    onCreate = { signUpSheet ->
                        viewModel.addSignUpSheet(signUpSheet)
                        showCreateSignUpSheetItemDialog.value = false
                    },
                    onCancel = {
                        showCreateSignUpSheetItemDialog.value = false
                    }
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }

    @Composable
    fun SignUpSheetListItem(
        modifier: Modifier = Modifier,
        signUpSheet: SignUpSheet,
        onDelete: (signUpSheet: SignUpSheet) -> Unit
    ) {
        val context = LocalContext.current
        val dismissState = rememberSwipeToDismissBoxState(
            initialValue = SwipeToDismissBoxValue.Settled,
            confirmValueChange = {
                when(it) {
                    SwipeToDismissBoxValue.EndToStart -> {
                        onDelete(signUpSheet)
                        Toast.makeText(context, context.getString(R.string.label_remove_sign_up_sheet), Toast.LENGTH_SHORT).show()
                    }
                    SwipeToDismissBoxValue.StartToEnd -> return@rememberSwipeToDismissBoxState false
                    SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
                }
                return@rememberSwipeToDismissBoxState true
            },
            // positional threshold of 25%
            positionalThreshold = { it * .25f }
        )

        SwipeToDismissBox(
            state = dismissState,
            modifier = modifier,
            backgroundContent = {
                DismissBackground(dismissState)
            },
            enableDismissFromStartToEnd = false,
            content = {
                SignUpSheetItemUI(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    signUpSheet = signUpSheet
                )
            })
    }

    @Composable
    fun SignUpSheetItemUI(
        modifier: Modifier = Modifier,
        signUpSheet: SignUpSheet
    ) {
        Row (
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp)
                    .weight(.85f),
                text = signUpSheet.sheetName,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = PBCFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(start = 4.dp, end = 4.dp)
                    .weight(.15f),
                text = "${signUpSheet.signUpAvailabilityCount}",
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = PBCFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.End
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DismissBackground(dismissState: SwipeToDismissBoxState) {
        val color = when (dismissState.dismissDirection) {
            SwipeToDismissBoxValue.EndToStart -> Color(0xFFFF1744)
            SwipeToDismissBoxValue.StartToEnd -> Color.Transparent
            SwipeToDismissBoxValue.Settled -> Color.Transparent
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color)
                .padding(12.dp, 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier)
            Icon(
                Icons.Default.Delete,
                contentDescription = "delete"
            )
        }
    }
}