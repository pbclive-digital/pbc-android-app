package com.kavi.pbc.droid.auth.ui.invitation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.droid.color.palette.extension.shadow
import com.kavi.pbc.droid.auth.R
import com.kavi.pbc.droid.lib.common.ui.component.AppFilledButton
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import com.kavi.pbc.droid.network.session.Session
import javax.inject.Inject

class AuthInvite @Inject constructor() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AuthInviteBottomSheet(sheetState: SheetState, showSheet: MutableState<Boolean>, onRegister: () -> Unit) {
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
                if (Session.isLogIn()) {
                    Column {

                    }
                } else {
                    AuthInviteContent(onRegister = onRegister)
                }
            }
        }
    }

    @Composable
    fun AuthInviteContent(onRegister: () -> Unit) {
        Box (
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = stringResource(R.string.label_auth_join_us),
                    fontFamily = PBCFontFamily,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                HorizontalDivider(
                    modifier = Modifier.padding(2.dp),
                    thickness = 2.dp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.image_auth_invite),
                        contentDescription = "Contact us icon",
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(260.dp)
                    )
                }

                Text(
                    text = stringResource(R.string.phrase_auth_join_us),
                    fontFamily = PBCFontFamily,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                AppFilledButton(
                    modifier = Modifier.padding(top = 16.dp),
                    label = stringResource(R.string.label_auth_join_us)
                ) {
                    onRegister.invoke()
                }
            }
        }
    }
}