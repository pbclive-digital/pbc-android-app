package com.kavi.pbc.droid.event.ui.selected

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.lib.common.ui.component.Title

@Composable
fun EventUI(navController: NavController, eventData: Event? = null) {
    Box (
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = 56.dp, start = 16.dp, end = 16.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Title(
                modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                titleText = "${eventData?.name}",
            )
        }
    }
}