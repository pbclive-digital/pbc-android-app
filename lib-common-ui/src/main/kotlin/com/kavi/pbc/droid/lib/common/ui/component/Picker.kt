package com.kavi.pbc.droid.lib.common.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePickerDialog(
    showDatePicker: MutableState<Boolean>,
    datePickerState: DatePickerState,
    onConfirmAction: () -> Unit,
    onDismissAction: () -> Unit
) {
    DatePickerDialog(
        onDismissRequest = { showDatePicker.value = false },
        confirmButton = {
            TextButton(onClick = {
                // Handle selected date from datePickerState.selectedDateMillis
                onConfirmAction.invoke()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissAction.invoke() }) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTimePickerDialog (
    onConfirm: (hour: Int, minute: Int) -> Unit,
    onDismiss: () -> Unit
) {
    val currentTime = Calendar.getInstance()
    val initialHour = currentTime.get(Calendar.HOUR_OF_DAY)
    val initialMinute = currentTime.get(Calendar.MINUTE)

    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = false // Set to true for 24-hour format
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Time") },
        text = {
            TimePicker(state = timePickerState)
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(timePickerState.hour, timePickerState.minute)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}