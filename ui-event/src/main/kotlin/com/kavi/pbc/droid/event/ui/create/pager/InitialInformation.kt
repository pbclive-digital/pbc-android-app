package com.kavi.pbc.droid.event.ui.create.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kavi.pbc.droid.data.dto.event.EventType
import com.kavi.pbc.droid.data.dto.event.VenueType
import com.kavi.pbc.droid.event.R
import com.kavi.pbc.droid.event.data.model.TimePickerMode
import com.kavi.pbc.droid.event.ui.create.EventCreateViewModel
import com.kavi.pbc.droid.lib.common.ui.component.AppDatePickerButton
import com.kavi.pbc.droid.lib.common.ui.component.AppDatePickerDialog
import com.kavi.pbc.droid.lib.common.ui.component.AppDropDownMenu
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineMultiLineTextField
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineTextField
import com.kavi.pbc.droid.lib.common.ui.component.AppTimePickerButton
import com.kavi.pbc.droid.lib.common.ui.component.AppTimePickerDialog
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import javax.inject.Inject

class InitialInformation @Inject constructor() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun InitialInformationUI(viewModel: EventCreateViewModel = hiltViewModel()) {
        val datePickerState = rememberDatePickerState()
        val showDatePicker = remember { mutableStateOf(false) }

        var showTimePicker by remember { mutableStateOf(false) }

        val eventName = remember { mutableStateOf(TextFieldValue(viewModel.newEvent.value.name)) }
        val eventDescription = remember { mutableStateOf(TextFieldValue(viewModel.newEvent.value.description)) }
        val eventType = remember { mutableStateOf(viewModel.getInitialEventType()) }
        val eventDate = remember { mutableStateOf(viewModel.getInitialEventDate()) }
        val eventFrom = remember { mutableStateOf(viewModel.getInitialStartTime()) }
        val eventTo = remember { mutableStateOf(viewModel.getInitialEndTime()) }
        val venueType = remember { mutableStateOf(viewModel.getInitialVenueType()) }
        val eventVenue = remember { mutableStateOf(TextFieldValue(viewModel.newEvent.value.venue ?: run { "" })) }
        val eventVenueAddress = remember { mutableStateOf(TextFieldValue(viewModel.newEvent.value.venueAddress ?: run { "" })) }
        val eventMeetingUrl = remember { mutableStateOf(TextFieldValue(viewModel.newEvent.value.meetingUrl ?: run { "" })) }

        var timePickerMode by remember { mutableStateOf(TimePickerMode.UNSELECTED) }

        LaunchedEffect(eventType.value) {
            viewModel.updateEventType(eventType = eventType.value)
        }

        LaunchedEffect(venueType.value) {
            viewModel.updateVenueType(venueType = venueType.value)
        }

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
                AppOutlineTextField (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    headingText = stringResource(R.string.label_title).uppercase(),
                    contentText = eventName,
                    onValueChange = { newValue ->
                        eventName.value = newValue
                        viewModel.updateName(eventName.value.text)
                    }
                )

                AppOutlineMultiLineTextField (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                        .height(100.dp),
                    headingText = stringResource(R.string.label_description).uppercase(),
                    contentText = eventDescription,
                    maxLines = 6,
                    onValueChange = { newValue ->
                        eventDescription.value = newValue
                        viewModel.updateDescription(eventDescription.value.text)
                    }
                )

                AppDropDownMenu(
                    modifier = Modifier
                        .padding(top = 4.dp),
                    title = stringResource(R.string.label_event_type).uppercase(),
                    selectableItems = listOf(EventType.BUDDHISM_CLASS.name, EventType.MEDITATION.name,
                        EventType.DHAMMA_TALK.name, EventType.SPECIAL.name),
                    selectedItem = eventType,
                )

                Text(
                    text = stringResource(R.string.label_when),
                    fontFamily = PBCFontFamily,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                )

                HorizontalDivider(
                    modifier = Modifier.padding(2.dp),
                    thickness = 2.dp
                )

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, start = 4.dp, end = 4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.label_date),
                        fontFamily = PBCFontFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    AppDatePickerButton (
                        modifier = Modifier.width(200.dp),
                        label = eventDate
                    ) {
                        showDatePicker.value = true
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, start = 4.dp, end = 4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppTimePickerButton(
                        modifier = Modifier
                            .weight(1f)
                            .width(150.dp),
                        label = eventFrom
                    ) {
                        showTimePicker = true
                        timePickerMode = TimePickerMode.FROM
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    AppTimePickerButton(
                        modifier = Modifier
                            .weight(1f)
                            .width(150.dp),
                        label = eventTo
                    ) {
                        showTimePicker = true
                        timePickerMode = TimePickerMode.TO
                    }
                }

                Text(
                    text = stringResource(R.string.label_where),
                    fontFamily = PBCFontFamily,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                )

                HorizontalDivider(
                    modifier = Modifier.padding(2.dp),
                    thickness = 2.dp
                )

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, start = 4.dp, end = 4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.label_venue_type),
                        fontFamily = PBCFontFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    AppDropDownMenu(
                        modifier = Modifier.width(200.dp),
                        title = stringResource(R.string.label_venue_type).uppercase(),
                        selectableItems = listOf(VenueType.VIRTUAL.name, VenueType.PHYSICAL.name),
                        selectedItem = venueType
                    )
                }

                when (venueType.value) {
                    VenueType.DEFAULT.name -> {}
                    VenueType.PHYSICAL.name -> {
                        AppOutlineTextField (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            headingText = stringResource(R.string.label_venue).uppercase(),
                            contentText = eventVenue,
                            onValueChange = { newValue ->
                                eventVenue.value = newValue
                                viewModel.updateVenue(eventVenue.value.text)
                            }
                        )

                        AppOutlineTextField (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            headingText = stringResource(R.string.label_venue_address).uppercase(),
                            contentText = eventVenueAddress,
                            onValueChange = { newValue ->
                                eventVenueAddress.value = newValue
                                viewModel.updateVenueAddress(eventVenueAddress.value.text)
                            }
                        )
                    }
                    VenueType.VIRTUAL.name -> {
                        AppOutlineTextField (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            headingText = stringResource(R.string.label_meeting_url).uppercase(),
                            contentText = eventMeetingUrl,
                            onValueChange = { newValue ->
                                eventMeetingUrl.value = newValue
                                viewModel.updateMeetingUrl(eventMeetingUrl.value.text)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
            }
        }

        if (showDatePicker.value) {
            AppDatePickerDialog (
                showDatePicker = showDatePicker,
                datePickerState = datePickerState,
                onConfirmAction = {
                    showDatePicker.value = false
                    eventDate.value = viewModel.formatDate(datePickerState.selectedDateMillis)
                    viewModel.updateDate(datePickerState.selectedDateMillis)
                },
                onDismissAction = {
                    showDatePicker.value = false
                }
            )
        }

        if (showTimePicker) {
            AppTimePickerDialog(
                onConfirm = { hour, minute ->
                    //selectedTime = String.format("%02d:%02d", hour, minute)
                    showTimePicker = false
                    when(timePickerMode) {
                        TimePickerMode.FROM -> {
                            eventFrom.value = viewModel.formatTime(hour = hour, minute = minute)
                            timePickerMode = TimePickerMode.UNSELECTED
                            viewModel.updateStartTime(eventFrom.value)
                        }
                        TimePickerMode.TO -> {
                            eventTo.value = viewModel.formatTime(hour = hour, minute = minute)
                            timePickerMode = TimePickerMode.UNSELECTED
                            viewModel.updateEndTime(eventTo.value)
                        }
                        TimePickerMode.UNSELECTED -> {
                            // Nothing will set
                        }
                    }
                },
                onDismiss = { showTimePicker = false }
            )
        }
    }
}