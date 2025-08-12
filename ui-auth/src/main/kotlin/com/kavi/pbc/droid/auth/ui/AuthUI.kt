package com.kavi.pbc.droid.auth.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.pbc.droid.auth.R
import com.kavi.pbc.droid.lib.common.ui.component.AppBlueFilledButton
import com.kavi.pbc.droid.lib.common.ui.theme.PBCNameFontFamily

@Composable
fun AuthUI() {
    BoxWithConstraints (
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        val screenSize = this.maxWidth
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
                stringResource(com.kavi.pbc.droid.lib.common.ui.R.string.label_name),
                fontFamily = PBCNameFontFamily,
                fontSize = 28.sp,
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