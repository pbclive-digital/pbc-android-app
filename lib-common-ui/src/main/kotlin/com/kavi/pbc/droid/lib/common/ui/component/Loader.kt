package com.kavi.pbc.droid.lib.common.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kavi.droid.color.palette.util.ColorUtil

@Composable
fun AppLoader(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.4f)),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}