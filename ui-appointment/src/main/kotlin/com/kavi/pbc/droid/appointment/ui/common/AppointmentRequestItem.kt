package com.kavi.pbc.droid.appointment.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.droid.color.palette.extension.shadow
import com.kavi.pbc.droid.data.dto.appointment.AppointmentRequest
import com.kavi.pbc.droid.data.dto.appointment.AppointmentRequestType
import com.kavi.pbc.droid.data.dto.user.User
import com.kavi.pbc.droid.data.dto.user.UserType
import com.kavi.pbc.droid.lib.common.ui.R
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import com.kavi.pbc.droid.network.session.Session

@Suppress("COMPOSE_APPLIER_CALL_MISMATCH")
@Composable
fun AppointmentRequestItem(modifier: Modifier = Modifier, appointmentReq: AppointmentRequest,
                          onView:() -> Unit, onDelete:() -> Unit, onModify:() -> Unit, onAccept:() -> Unit) {

    BoxWithConstraints (
        modifier = modifier.padding(top = 2.dp)
    ) {
        Row (
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .border(1.dp, MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(8.dp))
                .shadow(
                    elevation = 8.dp,
                    spotColor = MaterialTheme.colorScheme.shadow,
                    shape = RoundedCornerShape(8.dp),
                )
                .background(MaterialTheme.colorScheme.background),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                val titleText = Session.user?.let {
                    if (it.residentMonk) {
                        "${appointmentReq.user.firstName} requested an Appointment"
                    } else {
                        appointmentReq.selectedMonk?.let { monk ->
                            "Appointment request with Bhanthe ${monk.lastName}"
                        }?: run {
                            "Appointment request with PBC"
                        }
                    }
                }?: run {
                    ""
                }

                Row {
                    Text(
                        text = titleText,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = PBCFontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                val secondaryText = when(appointmentReq.appointmentReqType) {
                    AppointmentRequestType.REMOTE -> "Available to have an online appointment."
                    AppointmentRequestType.ON_SITE -> "Requesting an in-person appointment."
                }

                Row {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = secondaryText,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = PBCFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Session.user?.let { user ->
                            if (user.residentMonk) {
                                Icon(
                                    painter = painterResource(R.drawable.icon_view),
                                    contentDescription = "View User",
                                    tint = MaterialTheme.colorScheme.shadow,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .padding(4.dp)
                                        .clickable {
                                            onView.invoke()
                                        }
                                )

                                Icon(
                                    painter = painterResource(R.drawable.icon_accept),
                                    contentDescription = "Accept Event",
                                    tint = MaterialTheme.colorScheme.shadow,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .padding(4.dp)
                                        .clickable {
                                            onAccept.invoke()
                                        }
                                )
                            } else {
                                Icon(
                                    painter = painterResource(R.drawable.icon_edit),
                                    contentDescription = "Edit Event",
                                    tint = MaterialTheme.colorScheme.shadow,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .padding(4.dp)
                                        .clickable {
                                            onModify.invoke()
                                        }
                                )
                            }
                        }
                        Icon(
                            painter = painterResource(R.drawable.icon_delete),
                            contentDescription = "Delete Event",
                            tint = MaterialTheme.colorScheme.shadow,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(4.dp)
                                .clickable {
                                    onDelete.invoke()
                                }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Preview_AppointmentRequestItem() {
    Session.user = User(email = "test@gmail.com")
    AppointmentRequestItem(
        appointmentReq = AppointmentRequest(title = "", selectedMonk = null, selectedMonkId = "",
            reason = "", userId = "",
            user = User(email = "", firstName = "TEST", userType = UserType.MONK, residentMonk = true)),
        onView = {}, onAccept = {}, onDelete = {}, onModify = {}
    )
}