package com.kavi.pbc.droid.event.ui.create.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.pbc.droid.data.dto.event.signup.SignUpSheet
import com.kavi.pbc.droid.event.R
import com.kavi.pbc.droid.lib.common.ui.component.AppBasicDialog
import com.kavi.pbc.droid.lib.common.ui.component.AppFilledButton
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineButton
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineMultiLineTextField
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineTextField
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import java.util.UUID

@Composable
fun SignUpSheetCreateDialog(
    showDialog: MutableState<Boolean>,
    onCreate: (signUpSheetItem: SignUpSheet) -> Unit,
    onCancel: () -> Unit
) {
    AppBasicDialog(
        showDialog = showDialog.value,
        onDismissRequest = {
            onCancel.invoke()
        }
    ) {
        SignUpSheetCreateContent(
            onCreate = onCreate,
            onCancel = onCancel
        )
    }
}

@Composable
fun SignUpSheetCreateContent(onCreate: (signUpSheetItem: SignUpSheet) -> Unit, onCancel: () -> Unit) {

    val signUpSheetName = remember { mutableStateOf(TextFieldValue("")) }
    val signUpSheetDescription = remember { mutableStateOf(TextFieldValue("")) }
    val signUpAvailabilityCount = remember { mutableStateOf(TextFieldValue("")) }

    Box (
        modifier = Modifier
            .padding(24.dp)
    ) {
        Column {
            Text(
                text = stringResource(R.string.label_new_sign_up_sheet),
                fontSize = 24.sp,
                fontFamily = PBCFontFamily,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(R.string.phrase_new_sign_up_sheet),
                fontSize = 18.sp,
                fontFamily = PBCFontFamily,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface
            )

            AppOutlineTextField (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                headingText = stringResource(R.string.label_sheet_name).uppercase(),
                contentText = signUpSheetName,
                onValueChange = { newValue ->
                    signUpSheetName.value = newValue
                }
            )

            AppOutlineMultiLineTextField (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(150.dp),
                headingText = stringResource(R.string.label_sheet_dec).uppercase(),
                maxLines = 8,
                contentText = signUpSheetDescription,
                onValueChange = { newValue ->
                    signUpSheetDescription.value = newValue
                }
            )

            AppOutlineTextField (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                headingText = stringResource(R.string.label_sheet_availability_count).uppercase(),
                contentText = signUpAvailabilityCount,
                keyboardType = KeyboardType.Number,
                onValueChange = { newValue ->
                    signUpAvailabilityCount.value = newValue
                }
            )

            Row (
                modifier = Modifier.padding(top = 16.dp),
            ) {
                AppFilledButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp),
                    label = stringResource(com.kavi.pbc.droid.lib.common.ui.R.string.label_create)
                ) {
                    onCreate.invoke(
                        SignUpSheet(
                            UUID.randomUUID().toString(),
                            signUpSheetName.value.text,
                            signUpSheetDescription.value.text,
                            signUpAvailabilityCount.value.text.toInt()
                        )
                    )
                }

                AppOutlineButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp),
                    label = stringResource(com.kavi.pbc.droid.lib.common.ui.R.string.label_cancel)
                ) {
                    onCancel.invoke()
                }
            }
        }
    }
}