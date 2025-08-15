package com.kavi.pbc.droid.dashboard.ui.screen.event.pager

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.kavi.pbc.droid.dashboard.R
import com.kavi.pbc.droid.lib.common.ui.theme.PBCNameFontFamily

@Composable
fun UpcomingEventPager() {
    Text(
        "Upcoming",
        fontFamily = PBCNameFontFamily,
        fontSize = 32.sp,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun PastEventPager() {
    Text(
        "Past",
        fontFamily = PBCNameFontFamily,
        fontSize = 32.sp,
        color = MaterialTheme.colorScheme.onBackground
    )
}