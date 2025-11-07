package com.kavi.pbc.droid.lib.common.ui.component.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.lib.common.ui.R
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily

@Suppress("COMPOSE_APPLIER_CALL_MISMATCH")
@Composable
fun EventItem(modifier: Modifier = Modifier, event: Event) {
    BoxWithConstraints (
        modifier = modifier.padding(4.dp)
    ) {
        val screenWidth = this.maxWidth

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenWidth),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                // 1. Background Image
                AsyncImage(
                    model = event.eventImage,
                    error = painterResource(R.drawable.icon_pbc),
                    contentDescription = null, // decorative image
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background)
                )

                // 2. Gradient Overlay for text legibility
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                                startY = screenWidth.value / 2
                            )
                        )
                )

                // 3. Text content layered on top
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = event.name,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = PBCFontFamily,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        modifier = Modifier
                            .width((screenWidth.value * 0.65).dp),
                        text = event.description,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = PBCFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = "on ${event.getFormatDate()} at ${event.getPlace()}",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = PBCFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun Preview_EventItem() {
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