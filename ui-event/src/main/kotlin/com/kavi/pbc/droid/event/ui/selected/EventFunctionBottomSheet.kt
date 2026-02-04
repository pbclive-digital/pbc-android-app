package com.kavi.pbc.droid.event.ui.selected

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.kavi.pbc.droid.event.ui.common.EventPotluckItemUI
import com.kavi.pbc.droid.lib.common.ui.component.AppFilledButton
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import com.kavi.pbc.droid.network.session.Session
import java.util.Locale
import javax.inject.Inject

class EventFunctionBottomSheet @Inject constructor() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PotluckSheetUI(sheetState: SheetState, showSheet: MutableState<Boolean>,
                       viewModel: EventSelectedViewModel = hiltViewModel()) {

        val eventPotluckData by viewModel.eventPotluckData.collectAsState()

        val potluckItemCount = eventPotluckData.potluckItemList.size

        val lazyColumHeight = if (potluckItemCount <= 3) {
            400.dp
        } else if(potluckItemCount in 4..6) {
            500.dp
        } else {
            600.dp
        }

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
                    Column {
                        Text(
                            text = stringResource(R.string.label_event_contribute_potluck),
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

                        Text(
                            text = stringResource(R.string.phrase_event_contribute_potluck),
                            fontFamily = PBCFontFamily,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Justify,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )

                        LazyColumn (
                            modifier = Modifier
                                .padding(top = 12.dp)
                                .height(lazyColumHeight)
                        ) {
                            items(eventPotluckData.potluckItemList) { potluckItem ->
                                EventPotluckItemUI(
                                    modifier = Modifier.padding(bottom = 8.dp),
                                    viewModel = viewModel,
                                    potluckItem = potluckItem,
                                    currentUserContributions = viewModel
                                        .checkedCurrentUserContribution(potluckItem = potluckItem)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SignUpSheetBottomSheetUI(sheetState: SheetState,
                                 showSheet: MutableState<Boolean>,
                                 selectedSignUpSheet: EventSignUpSheet,
                                 viewModel: EventSelectedViewModel = hiltViewModel()) {
        val actionStatus by viewModel.actionFunctionStatus.collectAsState()
        var isLoading by remember { mutableStateOf(false) }

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
                    val isSignUp = viewModel.isCurrentUserSignUpToSignUpSheet(selectedSignUpSheet.sheetId)
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
                            horizontalArrangement = Arrangement.Center
                        ) {
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

                        if (isLoading) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        } else {
                            AppFilledButton(
                                modifier = Modifier.padding(top = 16.dp),
                                label = if (isSignUp)
                                    stringResource(R.string.label_event_sign_out) else stringResource(R.string.label_event_sign_up)) {

                                isLoading = true

                                if (isSignUp)
                                    viewModel.signOutFromSheet(selectedSignUpSheet.sheetId)
                                else
                                    viewModel.signUpToSheet(selectedSignUpSheet.sheetId)
                            }
                        }
                    }
                }
            }
        }

        if (actionStatus) {
            isLoading = false // This is necessary to make it false, to stop the loading indicator.
            viewModel.revokeActionFunctionStatus()
        }
    }
}