package com.kavi.pbc.droid.appointment.ui.manage

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kavi.droid.color.palette.extension.quaternary
import com.kavi.pbc.droid.appointment.R
import com.kavi.pbc.droid.appointment.data.model.AppointmentDeleteStatus
import com.kavi.pbc.droid.appointment.data.model.AppointmentReqDeleteStatus
import com.kavi.pbc.droid.appointment.data.repository.local.AppointmentLocalRepository
import com.kavi.pbc.droid.appointment.ui.common.AppointmentItem
import com.kavi.pbc.droid.appointment.ui.common.AppointmentRequestItem
import com.kavi.pbc.droid.data.dto.appointment.Appointment
import com.kavi.pbc.droid.data.dto.appointment.AppointmentRequest
import com.kavi.pbc.droid.data.dto.user.User
import com.kavi.pbc.droid.lib.common.ui.component.AppButtonWithIcon
import com.kavi.pbc.droid.lib.common.ui.component.AppFullScreenLoader
import com.kavi.pbc.droid.lib.common.ui.component.Title
import com.kavi.pbc.droid.lib.common.ui.component.user.UserViewBottomSheet
import com.kavi.pbc.droid.lib.common.ui.theme.BottomNavBarHeight
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import com.kavi.pbc.droid.network.session.Session
import javax.inject.Inject

class AppointmentManage @Inject constructor(
    private val appointmentLocalRepository: AppointmentLocalRepository
) {

    @Suppress("COMPOSE_APPLIER_CALL_MISMATCH")
    @Composable
    fun AppointmentManageUI(navController: NavController, viewModel: AppointmentManageViewModel = hiltViewModel()) {

        val context = LocalContext.current

        val eligibleToCreateRequest by viewModel.eligibleToCreateRequest.collectAsState()
        val appointmentDeleteStatus by viewModel.appointmentDeleteStatus.collectAsState()
        val appointmentReqDeleteStatus by viewModel.appointmentReqDeleteStatus.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.getRequestCreateEligibility()
            viewModel.fetchAppointmentList()
            viewModel.fetchAppointmentRequestList()
        }

        Box {
            BoxWithConstraints(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(top = 56.dp, start = 16.dp, end = 16.dp)
                    .fillMaxSize()
            ) {
                val screenHeight = this.maxHeight
                val screenWidth = this.maxWidth

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Title(
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                        titleText = stringResource(R.string.label_appointments),
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 30.dp)
                            .verticalScroll(state = rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.phase_appointments),
                            fontFamily = PBCFontFamily,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Justify,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        Session.user?.let { user ->
                            if (!user.residentMonk) {
                                AppButtonWithIcon(
                                    modifier = Modifier.padding(top = 12.dp),
                                    label = stringResource(R.string.label_appointment_make_request),
                                    icon = painterResource(R.drawable.icon_appointment)
                                ) {
                                    if (eligibleToCreateRequest)
                                        navController.navigate("appointment/appointment-request-create")
                                    else
                                        Toast.makeText(context, context.getString(R.string.phrase_appointment_create_stop), Toast.LENGTH_LONG).show()
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        AppointmentPager(screenWidth = screenWidth,
                            screenHeight = screenHeight, navController = navController)
                    }
                }
            }

            when(appointmentDeleteStatus) {
                AppointmentDeleteStatus.NONE -> {}
                AppointmentDeleteStatus.PENDING -> {
                    AppFullScreenLoader()
                }
                AppointmentDeleteStatus.FAILURE -> {
                    Toast.makeText(context, stringResource(R.string.label_appointment_delete_failed), Toast.LENGTH_LONG).show()
                    viewModel.resetAppointmentDeleteStatus()
                }
                AppointmentDeleteStatus.SUCCESS -> {
                    Toast.makeText(context, stringResource(R.string.label_appointment_delete_success), Toast.LENGTH_LONG).show()
                    viewModel.resetAppointmentDeleteStatus()
                }
            }

            when(appointmentReqDeleteStatus) {
                AppointmentReqDeleteStatus.NONE -> {}
                AppointmentReqDeleteStatus.PENDING -> {
                    AppFullScreenLoader()
                }
                AppointmentReqDeleteStatus.FAILURE -> {
                    Toast.makeText(context, stringResource(R.string.label_appointment_req_delete_failed), Toast.LENGTH_LONG).show()
                    viewModel.resetAppointmentReqDeleteStatus()
                }
                AppointmentReqDeleteStatus.SUCCESS -> {
                    Toast.makeText(context, stringResource(R.string.label_appointment_req_delete_success), Toast.LENGTH_LONG).show()
                    viewModel.resetAppointmentReqDeleteStatus()
                }
            }
        }
    }

    @Composable
    private fun AppointmentPager(screenWidth: Dp,
                                 screenHeight: Dp, navController: NavController) {

        var selectedPagerIndex by rememberSaveable { mutableIntStateOf(0) }
        val state = rememberPagerState { 2 }

        LaunchedEffect(selectedPagerIndex) {
            state.animateScrollToPage(selectedPagerIndex)
        }

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 12.dp, end = 12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            selectedPagerIndex = 0
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.label_appointment_requests),
                        fontFamily = PBCFontFamily,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            selectedPagerIndex = 1
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.label_appointments),
                        fontFamily = PBCFontFamily,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp)
            ) {
                repeat(state.pageCount) { iteration ->
                    val color = if (state.currentPage == iteration)
                        MaterialTheme.colorScheme.quaternary else MaterialTheme.colorScheme.surface

                    Box(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .height(2.dp)
                            .width((screenWidth - 20.dp) / 2)
                            .fillMaxWidth()
                            .background(color)
                    )
                }
            }
        }

        HorizontalPager(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            contentPadding = PaddingValues(horizontal = 0.dp),
            snapPosition = SnapPosition.Center
        ) { page ->
            when (page) {
                0 -> AppointmentRequestList(screenHeight = screenHeight, navController = navController)
                1 -> AppointmentList(screenHeight = screenHeight, navController = navController)
            }
        }
    }

    @Composable
    private fun AppointmentList(screenHeight: Dp,
                                navController: NavController,
                                viewModel: AppointmentManageViewModel = hiltViewModel()) {

        val appointmentList by viewModel.userAppointmentList.collectAsState()

        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight - BottomNavBarHeight)
                .padding(bottom = 20.dp)
        ) {
            items(appointmentList) { appointment ->
                AppointmentItem(
                    appointment = appointment,
                    onModify = {
                        val tempAppointmentKey = appointmentLocalRepository.setModifyingAppointment(appointment = appointment)
                        navController.navigate("appointment/appointment-edit/$tempAppointmentKey")
                    },
                    onDelete = {
                        viewModel.deleteAppointment(appointmentId = appointment.id!!)
                    }
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AppointmentRequestList(
        screenHeight: Dp,
        navController: NavController,
        viewModel: AppointmentManageViewModel = hiltViewModel()
    ) {
        val appointmentRequestList by viewModel.userAppointmentRequestList.collectAsState()

        val viewUserSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
        val showViewSheet = remember { mutableStateOf(false) }
        var selectedAppointmentReq by remember { mutableStateOf(AppointmentRequest(user = User(email = ""))) }

        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight - BottomNavBarHeight)
                .padding(bottom = 20.dp)
        ) {
            items(appointmentRequestList) { appointmentReq ->
                AppointmentRequestItem(
                    appointmentReq = appointmentReq,
                    onModify = {
                        val tempAppointmentKey = appointmentLocalRepository.setModifyingAppointmentRequest(appointmentReq = appointmentReq)
                        navController.navigate("appointment/appointment-request-edit/$tempAppointmentKey")
                    },
                    onDelete = {
                        viewModel.deleteAppointmentRequest(appointmentReqId = appointmentReq.id!!)
                    },
                    onView = {
                        showViewSheet.value = true
                        selectedAppointmentReq = appointmentReq
                    },
                    onAccept = {
                        val appointment = Appointment(
                            title = appointmentReq.title,
                            userId = appointmentReq.userId,
                            user = appointmentReq.user,
                            selectedMonkId = appointmentReq.selectedMonkId,
                            selectedMonk = appointmentReq.selectedMonk,
                            reason = appointmentReq.reason
                        )

                        val tempAppointmentKey = appointmentLocalRepository.setModifyingAppointment(appointment = appointment)
                        navController.navigate("appointment/appointment-convert/$tempAppointmentKey/${appointmentReq.id}")
                    }
                )
            }
        }

        if (showViewSheet.value) {
            UserViewBottomSheet(sheetState = viewUserSheetState, showSheet = showViewSheet, selectedUser = selectedAppointmentReq.user)
        }
    }
}