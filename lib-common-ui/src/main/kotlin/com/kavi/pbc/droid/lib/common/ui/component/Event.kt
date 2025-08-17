package com.kavi.pbc.droid.lib.common.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kavi.pbc.droid.data.dto.event.Event

@Composable
fun EventItem(modifier: Modifier = Modifier, event: Event) {
    Row (
        modifier = modifier
            .padding(12.dp)
            .fillMaxWidth()
            //.height(80.dp)
            .border(1.dp, MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(8.dp))
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(8.dp),
            )
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column (
            modifier = modifier
                .padding(12.dp)
        ) {
            Text(text = event.name)
        }
    }
}

@Preview
@Composable
fun Preview_Event() {
    EventItem(event = Event(
        id = "evt:YgY:1754372246218",
        name = "Event Name",
        description = "This is an event create for test the UI",
        eventDate = 1759656973000,
        startTime = "10:00AM",
        endTime = "4:00PM",
        createdTime = 1754372238981,
        creator = ""
    ))
}