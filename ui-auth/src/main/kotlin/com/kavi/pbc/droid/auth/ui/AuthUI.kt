package com.kavi.pbc.droid.auth.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.droid.color.palette.extension.quaternary
import com.kavi.pbc.droid.auth.R
import com.kavi.pbc.droid.lib.common.ui.component.AppBlueFilledButton
import com.kavi.pbc.droid.lib.common.ui.theme.PBCNameFontFamily

@Composable
fun AuthUI() {
    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(com.kavi.pbc.droid.lib.common.ui.R.drawable.icon_pbc),
                contentDescription = "Pittsburgh Buddhist Center icon",
                modifier = Modifier
                    .size(240.dp)
                    .clip(CircleShape)
            )

            Text(
                stringResource(com.kavi.pbc.droid.lib.common.ui.R.string.label_pbc_part_one),
                fontFamily = PBCNameFontFamily,
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.quaternary,
                modifier = Modifier.padding(top = 12.dp)
            )

            Text(
                stringResource(com.kavi.pbc.droid.lib.common.ui.R.string.label_pbc_part_two),
                fontFamily = PBCNameFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, bottom = 32.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppBlueFilledButton(
                label = "Sign in with Google",
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Log.d("Auth", "Login with Google")
            }

            Text(
                text = stringResource(R.string.label_login_as_guest),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .clickable {
                        Log.d("Auth", "Login as Guest")
                    }
            )
        }
    }
}

@Preview
@Composable
fun AuthUI_Preview() {
    AuthUI()
}