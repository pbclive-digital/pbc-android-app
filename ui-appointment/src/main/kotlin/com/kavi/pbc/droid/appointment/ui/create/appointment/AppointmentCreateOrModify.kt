package com.kavi.pbc.droid.appointment.ui.create.appointment

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kavi.pbc.droid.appointment.R
import com.kavi.pbc.droid.appointment.data.model.AppointmentCreationStatus
import com.kavi.pbc.droid.lib.common.ui.component.AppDatePickerButton
import com.kavi.pbc.droid.lib.common.ui.component.AppDatePickerDialog
import com.kavi.pbc.droid.lib.common.ui.component.AppDropDownMenu
import com.kavi.pbc.droid.lib.common.ui.component.AppFilledButton
import com.kavi.pbc.droid.lib.common.ui.component.AppFullScreenLoader
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineMultiLineTextField
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineTextField
import com.kavi.pbc.droid.lib.common.ui.component.AppTimePickerButton
import com.kavi.pbc.droid.lib.common.ui.component.AppTimePickerDialog
import com.kavi.pbc.droid.lib.common.ui.component.Title
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import javax.inject.Inject

class AppointmentCreateOrModify @Inject constructor() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppointmentCreateOrModifyUI(navController: NavController,
                                    modifyingAppointmentKey: String? = null,
                                    viewModel: AppointmentCreateOrModifyViewModel = hiltViewModel()) {

        val context = LocalContext.current

        val residenceMonkList by viewModel.residenceMonkList.collectAsState()

        val datePickerState = rememberDatePickerState()
        val showDatePicker = remember { mutableStateOf(false) }

        var showTimePicker by remember { mutableStateOf(false) }

        var isModify by remember { mutableStateOf(false) }

        modifyingAppointmentKey?.let {
            isModify = true
            viewModel.setModifyingAppointment(appointmentKey = it)
        }

        val appointmentTitle = remember { mutableStateOf(TextFieldValue(viewModel.newAppointment.value.title)) }
        val appointmentDate = remember { mutableStateOf(viewModel.getInitialAppointmentDate()) }
        val appointmentTime = remember { mutableStateOf(viewModel.getInitialTime()) }
        val appointmentWith = remember { mutableStateOf(viewModel.getInitialSelectedMonk()) }
        val appointmentReason = remember { mutableStateOf(TextFieldValue(viewModel.newAppointment.value.reason)) }

        val appointmentCreationStatus by viewModel.appointmentCreationStatus.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.getResidentMonkList()
        }

        LaunchedEffect(appointmentWith.value) {
            viewModel.updateSelectedMonk(selectedMonkName = appointmentWith.value)
        }

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
                        titleText = stringResource(R.string.label_appointment_new),
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
                            headingText = stringResource(R.string.label_appointment_title).uppercase(),
                            contentText = appointmentTitle,
                            onValueChange = { newValue ->
                                appointmentTitle.value = newValue
                                viewModel.updateTitle(appointmentTitle.value.text)
                            }
                        )

                        Text(
                            text = stringResource(R.string.label_appointment_when),
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

                        Text(
                            text = stringResource(R.string.phase_appointment_when),
                            fontFamily = PBCFontFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .padding(top = 12.dp)
                                .fillMaxWidth()
                        )

                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp, start = 4.dp, end = 4.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AppDatePickerButton (
                                modifier = Modifier.width(200.dp),
                                label = appointmentDate
                            ) {
                                showDatePicker.value = true
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            AppTimePickerButton(
                                modifier = Modifier
                                    .width(150.dp),
                                label = appointmentTime
                            ) {
                                showTimePicker = true
                            }
                        }

                        Text(
                            text = stringResource(R.string.label_appointment_with),
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

                        Text(
                            text = stringResource(R.string.phase_appointment_with),
                            fontFamily = PBCFontFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .padding(top = 12.dp)
                                .fillMaxWidth()
                        )

                        AppDropDownMenu(
                            modifier = Modifier
                                .padding(top = 4.dp),
                            title = stringResource(R.string.label_appointment_with_select),
                            selectableItems = residenceMonkList,
                            selectedItem = appointmentWith,
                        )

                        Text(
                            text = stringResource(R.string.label_appointment_why),
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

                        Text(
                            text = stringResource(R.string.phase_appointment_why),
                            fontFamily = PBCFontFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .padding(top = 12.dp)
                                .fillMaxWidth()
                        )

                        AppOutlineMultiLineTextField (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp)
                                .height(120.dp),
                            headingText = stringResource(R.string.label_appointment_reason).uppercase(),
                            contentText = appointmentReason,
                            maxLines = 6,
                            onValueChange = { newValue ->
                                appointmentReason.value = newValue
                                viewModel.updateReason(appointmentReason.value.text)
                            }
                        )

                        AppFilledButton(
                            label = if (isModify) stringResource(R.string.label_appointment_modify)
                            else stringResource(R.string.label_appointment_create),
                            modifier = Modifier.padding(top = 12.dp)
                        ) {
                            viewModel.createNewAppointment()
                        }
                    }
                }
            }

            when(appointmentCreationStatus) {
                AppointmentCreationStatus.NONE -> {}
                AppointmentCreationStatus.PENDING -> {
                    AppFullScreenLoader()
                }
                AppointmentCreationStatus.UNAUTHENTICATE,
                AppointmentCreationStatus.FAILURE -> {
                    Toast.makeText(context, stringResource(R.string.label_appointment_create_failed), Toast.LENGTH_LONG).show()
                }
                AppointmentCreationStatus.SUCCESS -> {
                    navController.popBackStack()
                }
            }
        }

        if (showDatePicker.value) {
            AppDatePickerDialog (
                showDatePicker = showDatePicker,
                datePickerState = datePickerState,
                onConfirmAction = {
                    showDatePicker.value = false
                    appointmentDate.value = viewModel.formatDate(datePickerState.selectedDateMillis)
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
                    showTimePicker = false
                    appointmentTime.value = viewModel.formatTime(hour = hour, minute = minute)
                    viewModel.updateTime(appointmentTime.value)
                },
                onDismiss = { showTimePicker = false }
            )
        }
    }
}