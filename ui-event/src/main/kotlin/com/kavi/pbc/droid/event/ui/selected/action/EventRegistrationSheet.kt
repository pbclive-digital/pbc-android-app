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
import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.event.R
import com.kavi.pbc.droid.event.data.model.EventRegUnRegUiStatus
import com.kavi.pbc.droid.event.data.model.RegUnRegType
import com.kavi.pbc.droid.event.ui.selected.EventSelectedViewModel
import com.kavi.pbc.droid.lib.common.ui.component.AppFilledButton
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import com.kavi.pbc.droid.network.session.Session
import java.util.Locale
import javax.inject.Inject

class EventRegistrationSheet @Inject constructor() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun RegistrationSheetUI(sheetState: SheetState, showSheet: MutableState<Boolean>, viewModel: EventSelectedViewModel = hiltViewModel()) {

        val givenEvent by viewModel.givenEvent.collectAsState()
        val actionStatus by viewModel.eventRegUnRegStatus.collectAsState()
        val isCurrentUserRegistered = viewModel.isCurrentUserRegistered()

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
                        EventRegUnRegUiStatus.INITIAL -> {
                            RegUnRegInitialUI(viewModel = viewModel, isCurrentUserRegistered = isCurrentUserRegistered, givenEvent = givenEvent)
                        }
                        EventRegUnRegUiStatus.PENDING -> {
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
                        EventRegUnRegUiStatus.FAILURE -> {
                            RegUnRegFailure(viewModel = viewModel, showSheet = showSheet)
                        }
                        EventRegUnRegUiStatus.SUCCESS -> {
                            val regUnRegType = if (isCurrentUserRegistered) RegUnRegType.UNREGISTER else RegUnRegType.REGISTER
                            RegUnRegSuccess(viewModel = viewModel, regUnRegType = regUnRegType, showSheet = showSheet)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun RegUnRegInitialUI(viewModel: EventSelectedViewModel, isCurrentUserRegistered: Boolean, givenEvent: Event) {
        Column {
            Text(
                text = if (isCurrentUserRegistered)
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
                    painter = if (isCurrentUserRegistered)
                        painterResource(R.drawable.icon_remove_item)
                    else
                        painterResource(R.drawable.icon_add_item),
                    contentDescription = "Provided icon",
                    modifier = Modifier
                        .size(100.dp)
                )
            }

            Text(
                text = if (isCurrentUserRegistered)
                    String.format(Locale.US, stringResource(R.string.phrase_event_unregistering), givenEvent.name)
                else
                    String.format(Locale.US, stringResource(R.string.phrase_event_registering), givenEvent.name),
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
    }

    @Composable
    fun RegUnRegSuccess(viewModel: EventSelectedViewModel, regUnRegType: RegUnRegType, showSheet: MutableState<Boolean>) {
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
                viewModel.revokeEventRegUnRegStatus()
            }
        }
    }

    @Composable
    fun RegUnRegFailure(viewModel: EventSelectedViewModel, showSheet: MutableState<Boolean>) {
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
                viewModel.revokeEventRegUnRegStatus()
            }
        }
    }
}