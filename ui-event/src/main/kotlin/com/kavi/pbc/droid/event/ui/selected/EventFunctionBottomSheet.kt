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
import com.kavi.pbc.droid.event.R
import com.kavi.pbc.droid.lib.common.ui.component.AppFilledButton
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import com.kavi.pbc.droid.network.session.Session
import java.util.Locale
import javax.inject.Inject

class EventFunctionBottomSheet @Inject constructor() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PotluckSheetUI(sheetState: SheetState, showSheet: MutableState<Boolean>) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                showSheet.value = false
            },
            containerColor = MaterialTheme.colorScheme.background
        ) {
            Box (
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(start = 20.dp, end = 20.dp, bottom = 40.dp)
                    .fillMaxWidth()
            ) {
                if (Session.isLogIn()) {
                    Column {

                    }
                } else {
                    InviteToSignUpUI()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun RegistrationSheetUI(sheetState: SheetState, showSheet: MutableState<Boolean>, viewModel: EventSelectedViewModel = hiltViewModel()) {

        val registrationStatus by viewModel.actionFunctionStatus.collectAsState()
        var isLoading by remember { mutableStateOf(false) }

        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                showSheet.value = false
            },
            containerColor = MaterialTheme.colorScheme.background
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
                            text = if (viewModel.isCurrentUserRegistered())
                                stringResource(R.string.label_event_unregistering)
                            else
                                stringResource(R.string.label_event_registering),
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
                                painter = if (viewModel.isCurrentUserRegistered())
                                    painterResource(R.drawable.icon_remove_item)
                                else
                                    painterResource(R.drawable.icon_add_item),
                                contentDescription = "Provided icon",
                                modifier = Modifier
                                    .size(100.dp)
                            )
                        }

                        Text(
                            text = if (viewModel.isCurrentUserRegistered())
                                String.format(Locale.US, stringResource(R.string.phrase_event_unregistering), viewModel.givenEvent.value.name)
                            else
                                String.format(Locale.US, stringResource(R.string.phrase_event_registering), viewModel.givenEvent.value.name),
                            fontFamily = PBCFontFamily,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )

                        Text(
                            text = String.format(Locale.US, stringResource(R.string.label_event_remaining_seats), viewModel.remainingSeatCountAvailable()),
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
                                label = if (viewModel.isCurrentUserRegistered())
                                    stringResource(R.string.label_event_unregister) else stringResource(R.string.label_event_register)) {

                                isLoading = true

                                if (viewModel.isCurrentUserRegistered())
                                    viewModel.unregisterFromEvent()
                                else
                                    viewModel.registerToEvent()
                            }
                        }
                    }
                } else {
                    InviteToSignUpUI()
                }
            }

            if (registrationStatus) {
                isLoading = false
                viewModel.revokeActionFunctionStatus()
            }
        }
    }

    @Composable
    fun InviteToSignUpUI() {

    }
}