package com.kavi.pbc.droid.event.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily

@Composable
fun EventAgendaItem(
    modifier: Modifier = Modifier,
    agendaItem: String
) {
    Row (
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp)
                .weight(.85f),
            text = agendaItem,
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = PBCFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}