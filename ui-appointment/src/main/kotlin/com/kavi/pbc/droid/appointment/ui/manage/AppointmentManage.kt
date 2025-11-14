package com.kavi.pbc.droid.appointment.ui.manage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kavi.pbc.droid.appointment.R
import com.kavi.pbc.droid.appointment.ui.common.AppointmentItem
import com.kavi.pbc.droid.lib.common.ui.component.AppButtonWithIcon
import com.kavi.pbc.droid.lib.common.ui.component.Title
import com.kavi.pbc.droid.lib.common.ui.theme.BottomNavBarHeight
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import javax.inject.Inject

class AppointmentManage @Inject constructor() {

    @Suppress("COMPOSE_APPLIER_CALL_MISMATCH")
    @Composable
    fun AppointmentManageUI(navController: NavController, viewModel: AppointmentManageViewModel = hiltViewModel()) {

        val appointmentList = viewModel.userAppointmentList.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.fetchAppointmentList()
        }

        BoxWithConstraints(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(top = 56.dp, start = 16.dp, end = 16.dp)
                .fillMaxSize()
        ) {
            val maxHeight = this.maxHeight

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

                    AppButtonWithIcon(
                        modifier = Modifier.padding(top = 12.dp),
                        label = stringResource(R.string.label_make_appointments),
                        icon = painterResource(R.drawable.icon_appointment)
                    ) {
                        navController.navigate("appointment/appointment-create")
                    }

                    LazyColumn (
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(maxHeight - BottomNavBarHeight)
                            .padding(top = 20.dp, bottom = 20.dp)
                    ) {
                        items(appointmentList.value) { appointment ->
                            AppointmentItem(appointment = appointment)
                        }
                    }
                }
            }
        }
    }
}