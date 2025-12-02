package com.kavi.pbc.droid.appointment.ui.create.request

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.kavi.pbc.droid.appointment.data.model.AppointmentReqCreationStatus
import com.kavi.pbc.droid.data.dto.appointment.AppointmentRequestType
import com.kavi.pbc.droid.lib.common.ui.component.AppDropDownMenu
import com.kavi.pbc.droid.lib.common.ui.component.AppFilledButton
import com.kavi.pbc.droid.lib.common.ui.component.AppFullScreenLoader
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineMultiLineTextField
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineTextField
import com.kavi.pbc.droid.lib.common.ui.component.Title
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import javax.inject.Inject

class AppointmentRequestCreateOrModify @Inject constructor() {

    @Composable
    fun AppointmentRequestCreateOrModifyUI(
        navController: NavController,
        modifyingAppointmentReqKey: String? = null,
        viewModel: AppointmentRequestCreateOrModifyViewModel = hiltViewModel()
    ) {
        val context = LocalContext.current

        val residenceMonkList by viewModel.residenceMonkList.collectAsState()
        val newAppointmentReqCreateStatus by viewModel.newAppointmentReqCreateStatus.collectAsState()

        var isModify by remember { mutableStateOf(false) }

        modifyingAppointmentReqKey?.let {
            isModify = true
            viewModel.setModifyingAppointmentRequest(appointmentReqKey = it)
        }

        val appointmentTitle = remember { mutableStateOf(TextFieldValue(viewModel.newAppointmentRequest.value.title)) }
        val appointmentWith = remember { mutableStateOf(viewModel.getInitialSelectedMonk()) }
        val appointmentType = remember { mutableStateOf(viewModel.newAppointmentRequest.value.appointmentReqType.name ) }
        val appointmentReason = remember { mutableStateOf(TextFieldValue(viewModel.newAppointmentRequest.value.reason)) }

        LaunchedEffect(Unit) {
            viewModel.getResidentMonkList()
        }

        LaunchedEffect(appointmentWith.value) {
            viewModel.updateSelectedMonk(selectedMonkName = appointmentWith.value)
        }

        LaunchedEffect(appointmentType.value) {
            viewModel.updateAppointmentType(appointmentType = appointmentType.value)
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
                        titleText = stringResource(R.string.label_appointment_request),
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
                            text = stringResource(R.string.label_appointment_how),
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
                            text = stringResource(R.string.phrase_appointment_how),
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
                            title = stringResource(R.string.label_appointment_how_select),
                            selectableItems = listOf(
                                AppointmentRequestType.REMOTE.name, AppointmentRequestType.ON_SITE.name
                            ),
                            selectedItem = appointmentType,
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
                            headingText = stringResource(R.string.label_appointment_reason),
                            contentText = appointmentReason,
                            maxLines = 6,
                            onValueChange = { newValue ->
                                appointmentReason.value = newValue
                                viewModel.updateReason(appointmentReason.value.text)
                            }
                        )

                        AppFilledButton(
                            label = if (isModify) stringResource(R.string.label_appointment_request_modify)
                            else stringResource(R.string.label_appointment_request_create),
                            modifier = Modifier.padding(top = 12.dp)
                        ) {
                            viewModel.createNewAppointmentRequest()
                        }
                    }
                }
            }

            when(newAppointmentReqCreateStatus) {
                AppointmentReqCreationStatus.NONE -> {}
                AppointmentReqCreationStatus.PENDING -> {
                    AppFullScreenLoader()
                }
                AppointmentReqCreationStatus.UNAUTHENTICATE,
                AppointmentReqCreationStatus.FAILURE -> {
                    Toast.makeText(context, stringResource(R.string.label_appointment_create_failed), Toast.LENGTH_LONG).show()
                }
                AppointmentReqCreationStatus.SUCCESS -> {
                    navController.popBackStack()
                }
            }
        }
    }
}