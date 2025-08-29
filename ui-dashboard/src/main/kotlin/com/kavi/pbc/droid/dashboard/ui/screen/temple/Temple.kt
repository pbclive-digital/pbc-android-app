package com.kavi.pbc.droid.dashboard.ui.screen.temple

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kavi.pbc.droid.dashboard.R
import com.kavi.pbc.droid.lib.common.ui.component.TitleWithAction

@Composable
fun TempleUI(navController: NavController, modifier: Modifier = Modifier) {
    Box (
        modifier = modifier
            .padding(top = 56.dp, start = 16.dp, end = 16.dp)
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        TitleWithAction(
            modifier = Modifier.padding(start = 12.dp, end = 12.dp),
            titleText = stringResource(R.string.label_temple),
            icon = painterResource(com.kavi.pbc.droid.lib.common.ui.R.drawable.image_dhamma_chakra),
            iconAction = {}
        )
    }
}