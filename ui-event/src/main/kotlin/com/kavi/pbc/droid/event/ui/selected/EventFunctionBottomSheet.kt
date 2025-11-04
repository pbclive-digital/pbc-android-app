package com.kavi.pbc.droid.event.ui.selected

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
            }
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

        val registrationStatus by viewModel.registrationStatus.collectAsState()

        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                showSheet.value = false
            }
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
                            text = stringResource(R.string.label_event_registering),
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
                                painter = painterResource(R.drawable.image_worship),
                                contentDescription = "Provided icon",
                                modifier = Modifier
                                    .size(100.dp)
                            )
                        }

                        Text(
                            text = String.format(Locale.US, stringResource(R.string.phrase_event_registering), viewModel.givenEvent.value.name),
                            fontFamily = PBCFontFamily,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Justify,
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

                        AppFilledButton(
                            modifier = Modifier.padding(top = 16.dp),
                            label = if (viewModel.isCurrentUserRegistered())
                                stringResource(R.string.label_event_unregister) else stringResource(R.string.label_event_register)) {

                            if (viewModel.isCurrentUserRegistered())
                                viewModel.unregisterFromEvent()
                            else
                                viewModel.registerToEvent()
                        }
                    }
                } else {
                    InviteToSignUpUI()
                }
            }

            if (registrationStatus) {
                showSheet.value = false
            }
        }
    }

    @Composable
    fun InviteToSignUpUI() {

    }
}