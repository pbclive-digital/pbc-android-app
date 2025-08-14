package com.kavi.pbc.droid.splash.ui.error

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.pbc.droid.lib.common.ui.component.AppBlueFilledButton
import com.kavi.pbc.droid.lib.common.ui.theme.PBCNameFontFamily
import com.kavi.pbc.droid.ui.splash.R

@Composable
fun NoAPISupport() {
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
                painter = painterResource(R.drawable.image_update_required),
                contentDescription = "Pittsburgh Buddhist Center icon",
                modifier = Modifier
                    .padding(16.dp)
            )

            Text(
                stringResource(R.string.label_update_required),
                fontFamily = PBCNameFontFamily,
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 12.dp)
            )

            Text(
                stringResource(R.string.phrase_update_required),
                fontFamily = PBCNameFontFamily,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 40.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppBlueFilledButton(
                label = stringResource(R.string.label_update_pbc),
            ) {
                Log.d("Splash", "Navigate user to Google Play store")
            }
        }
    }
}

@Preview
@Composable
fun NoAPISupport_Preview() {
    NoAPISupport()
}