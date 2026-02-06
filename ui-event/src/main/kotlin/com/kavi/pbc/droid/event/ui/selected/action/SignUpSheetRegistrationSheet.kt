package com.kavi.pbc.droid.event.ui.selected.action

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kavi.droid.color.palette.extension.shadow
import com.kavi.pbc.droid.data.dto.event.signup.EventSignUpSheet
import com.kavi.pbc.droid.event.R
import com.kavi.pbc.droid.event.data.model.RegUnRegType
import com.kavi.pbc.droid.event.data.model.SignUpSheetRegUnRegUiStatus
import com.kavi.pbc.droid.event.ui.selected.EventSelectedViewModel
import com.kavi.pbc.droid.lib.common.ui.component.AppFilledButton
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineButton
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import com.kavi.pbc.droid.network.session.Session
import java.util.Locale
import javax.inject.Inject

class SignUpSheetRegistrationSheet @Inject constructor() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SignUpSheetBottomSheetUI(sheetState: SheetState,
                                 showSheet: MutableState<Boolean>,
                                 selectedSignUpSheet: EventSignUpSheet,
                                 viewModel: EventSelectedViewModel = hiltViewModel()) {

        val actionStatus by viewModel.signUpSheetRegUnRegStatus.collectAsState()

        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                showSheet.value = false
            },
            containerColor = MaterialTheme.colorScheme.background,
            scrimColor = MaterialTheme.colorScheme.shadow.copy(alpha = .5f)
        ) {
            Box (
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(start = 20.dp, end = 20.dp, bottom = 40.dp)
                    .fillMaxWidth()
            ) {
                if (Session.isLogIn()) {
                    when(actionStatus) {
                        SignUpSheetRegUnRegUiStatus.INITIAL -> {
                            SignUpSheetRegUnRegInitial(viewModel = viewModel, selectedSignUpSheet = selectedSignUpSheet)
                        }
                        SignUpSheetRegUnRegUiStatus.PENDING -> {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        SignUpSheetRegUnRegUiStatus.FAILURE -> {
                            SignUpSheetRegUnRegFailure(viewModel = viewModel, showSheet = showSheet)
                        }
                        SignUpSheetRegUnRegUiStatus.REG_SUCCESS -> {
                            SignUpSheetRegUnRegSuccess(viewModel = viewModel, regUnRegType = RegUnRegType.REGISTER, showSheet = showSheet)
                        }
                        SignUpSheetRegUnRegUiStatus.UN_REG_SUCCESS -> {
                            SignUpSheetRegUnRegSuccess(viewModel = viewModel, regUnRegType = RegUnRegType.UNREGISTER, showSheet = showSheet)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SignUpSheetRegUnRegInitial(viewModel: EventSelectedViewModel, selectedSignUpSheet: EventSignUpSheet) {
        var isSignUp = false
        var signUpCount = 0
        if (selectedSignUpSheet.allowMultiSignUps) {
            signUpCount = viewModel.currentUserSignUpCountToSignUpSheet(selectedSignUpSheet.sheetId)
        } else {
            isSignUp = viewModel.isCurrentUserSignUpToSignUpSheet(selectedSignUpSheet.sheetId)
        }

        Column {
            Column {
                Text(
                    text = String.format(Locale.US,
                        stringResource(R.string.label_event_sign_up_sheet_title),
                        selectedSignUpSheet.sheetName),
                    fontFamily = PBCFontFamily,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                HorizontalDivider(
                    modifier = Modifier.padding(2.dp),
                    thickness = 2.dp
                )

                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if(selectedSignUpSheet.allowMultiSignUps) {
                        Image(
                            painter = painterResource(R.drawable.icon_add_item),
                            contentDescription = "Provided icon",
                            modifier = Modifier
                                .size(80.dp)
                        )
                        Text(
                            text = stringResource(R.string.label_event_or),
                            fontFamily = PBCFontFamily,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(start = 4.dp, end = 8.dp)
                        )
                        Image(
                            painter = painterResource(R.drawable.icon_remove_item),
                            contentDescription = "Provided icon",
                            modifier = Modifier
                                .size(80.dp)
                        )
                    } else {
                        Image(
                            painter = if (isSignUp)
                                painterResource(R.drawable.icon_remove_item)
                            else
                                painterResource(R.drawable.icon_add_item),
                            contentDescription = "Provided icon",
                            modifier = Modifier
                                .size(100.dp)
                        )
                    }
                }

                Text(
                    text = selectedSignUpSheet.sheetDescription,
                    fontFamily = PBCFontFamily,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )

                Text(
                    text = String.format(Locale.US, stringResource(R.string.label_event_remaining_seats),
                        viewModel.remainingSignUpCountInSignUpSheet(selectedSignUpSheet.sheetId)),
                    fontFamily = PBCFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )

                if (selectedSignUpSheet.allowMultiSignUps) {
                    Text(
                        text = String.format(Locale.US, stringResource(R.string.label_event_user_sign_up_count),
                            signUpCount),
                        fontFamily = PBCFontFamily,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                }

                if (selectedSignUpSheet.allowMultiSignUps) {
                    Row (
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        AppOutlineButton(
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .weight(.5f),
                            label = stringResource(R.string.label_event_sign_out),
                            labelTextSize = 10.sp
                        ) {
                            viewModel.signOutFromSheet(selectedSignUpSheet.sheetId)
                        }
                        AppFilledButton(
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .weight(.5f),
                            label = stringResource(R.string.label_event_sign_up),
                            labelTextSize = 10.sp
                        ) {
                            viewModel.signUpToSheet(selectedSignUpSheet.sheetId)
                        }
                    }
                } else {
                    AppFilledButton(
                        modifier = Modifier.padding(top = 16.dp),
                        label = if (isSignUp)
                            stringResource(R.string.label_event_sign_out) else stringResource(R.string.label_event_sign_up)) {

                        if (isSignUp)
                            viewModel.signOutFromSheet(selectedSignUpSheet.sheetId)
                        else
                            viewModel.signUpToSheet(selectedSignUpSheet.sheetId)
                    }
                }
            }
        }
    }

    @Composable
    fun SignUpSheetRegUnRegSuccess(viewModel: EventSelectedViewModel, regUnRegType: RegUnRegType, showSheet: MutableState<Boolean>) {
        Column {
            Text(
                text = when(regUnRegType){
                    RegUnRegType.REGISTER -> stringResource(R.string.label_event_reg_success)
                    RegUnRegType.UNREGISTER -> stringResource(R.string.label_event_un_reg_success)
                },
                fontFamily = PBCFontFamily,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = when(regUnRegType) {
                        RegUnRegType.REGISTER -> painterResource(R.drawable.icon_event_added_success)
                        RegUnRegType.UNREGISTER -> painterResource(R.drawable.icon_event_removed_success)
                    },
                    contentDescription = "Provided icon",
                    modifier = Modifier
                        .size(100.dp)
                )
            }

            Text(
                text = when(regUnRegType){
                    RegUnRegType.REGISTER -> stringResource(R.string.phrase_event_reg_success)
                    RegUnRegType.UNREGISTER -> stringResource(R.string.phrase_event_un_reg_success)
                },
                fontFamily = PBCFontFamily,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            AppFilledButton(
                modifier = Modifier.padding(top = 16.dp),
                label = stringResource(R.string.label_event_close)
            ) {
                showSheet.value = false
                viewModel.revokeSignUpSheetRegUnRegStatus()
            }
        }
    }

    @Composable
    fun SignUpSheetRegUnRegFailure(viewModel: EventSelectedViewModel, showSheet: MutableState<Boolean>) {
        Column {
            Text(
                text = stringResource(R.string.label_event_reg_un_reg_failure),
                fontFamily = PBCFontFamily,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.icon_event_process_failed),
                    contentDescription = "Provided icon",
                    modifier = Modifier
                        .size(100.dp)
                )
            }

            Text(
                text = stringResource(R.string.phrase_event_reg_un_reg_failure),
                fontFamily = PBCFontFamily,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            AppFilledButton(
                modifier = Modifier.padding(top = 16.dp),
                label = stringResource(R.string.label_event_close)
            ) {
                showSheet.value = false
                viewModel.revokeSignUpSheetRegUnRegStatus()
            }
        }
    }
}