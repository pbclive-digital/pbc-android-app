package com.kavi.pbc.droid.appointment.ui.manage

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.droid.color.palette.extension.shadow
import com.kavi.pbc.droid.appointment.R
import com.kavi.pbc.droid.data.dto.appointment.Appointment
import com.kavi.pbc.droid.data.dto.user.User
import com.kavi.pbc.droid.lib.common.ui.component.AppFilledButton
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import javax.inject.Inject

class CalendarRequestBottomSheet @Inject constructor() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AskCalendarEventSheetUI(newAppointment: Appointment,
        sheetState: SheetState, showSheet: MutableState<Boolean>) {

        val context = LocalContext.current

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
                Column (
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.label_appointment_create_calendar_event),
                        fontFamily = PBCFontFamily,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        modifier = Modifier.padding(top = 12.dp),
                        text = stringResource(R.string.phrase_appointment_create_calendar_event),
                        fontFamily = PBCFontFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Image(
                        painter = painterResource(R.drawable.image_calendar),
                        contentDescription = "Calendar image",
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 4.dp)
                            .size(200.dp),
                        contentScale = ContentScale.Fit
                    )

                    AppFilledButton(
                        label = stringResource(R.string.label_appointment_on_calendar)
                    ) {
                        showSheet.value = false
                        addAppointmentToCalendar(context, appointment = newAppointment)
                    }
                }
            }
        }
    }

    fun addAppointmentToCalendar(context: Context, appointment: Appointment) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, appointment.title)
            putExtra(CalendarContract.Events.DESCRIPTION, appointment.reason)
            //putExtra(CalendarContract.Events.EVENT_LOCATION, location)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, appointment.date)
            //putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTimeMillis)
            putExtra(Intent.EXTRA_EMAIL, appointment.user.email)
            putExtra(CalendarContract.Attendees.ATTENDEE_EMAIL, appointment.user.email)
        }

        context.startActivity(intent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Preview_Sheet() {
    val showSheetState = rememberModalBottomSheetState()
    val showSheet = remember { mutableStateOf(false) }

    CalendarRequestBottomSheet().AskCalendarEventSheetUI(
        newAppointment = Appointment(user = User(email = "")), sheetState = showSheetState, showSheet
    )
}