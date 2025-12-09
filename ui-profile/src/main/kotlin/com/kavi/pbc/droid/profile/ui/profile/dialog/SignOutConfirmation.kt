package com.kavi.pbc.droid.profile.ui.profile.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.pbc.droid.lib.common.ui.component.AppBasicDialog
import com.kavi.pbc.droid.lib.common.ui.component.AppFilledButton
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineButton
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import com.kavi.pbc.droid.profile.R

@Composable
fun SignOutConfirmationDialog(
    showDialog: MutableState<Boolean>,
    onAgree: () -> Unit,
    onDisagree: () -> Unit
) {
    AppBasicDialog(
        showDialog = showDialog.value,
        onDismissRequest = {
            onDisagree.invoke()
        }
    ) {
        SignOutConfirmationContent(
            onAgree = onAgree,
            onDisagree = onDisagree
        )
    }
}

@Composable
private fun SignOutConfirmationContent(onAgree: () -> Unit, onDisagree: () -> Unit) {
    Box (
        modifier = Modifier
            .padding(24.dp)
    ) {
        Column {
            Text(
                text = stringResource(R.string.label_sure),
                fontSize = 24.sp,
                fontFamily = PBCFontFamily,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(R.string.phrase_sure_sign_out),
                fontSize = 18.sp,
                fontFamily = PBCFontFamily,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row (
                modifier = Modifier.padding(top = 16.dp),
            ) {
                AppOutlineButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp),
                    label = stringResource(com.kavi.pbc.droid.lib.common.ui.R.string.label_yes)
                ) {
                    onAgree.invoke()
                }
                AppFilledButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp),
                    label = stringResource(com.kavi.pbc.droid.lib.common.ui.R.string.label_no)
                ) {
                    onDisagree.invoke()
                }
            }
        }
    }
}