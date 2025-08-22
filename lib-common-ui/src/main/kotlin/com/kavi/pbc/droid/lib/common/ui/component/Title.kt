package com.kavi.pbc.droid.lib.common.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kavi.pbc.droid.lib.common.ui.R
import com.kavi.pbc.droid.lib.common.ui.theme.PBCNameFontFamily

@Composable
fun Title(modifier: Modifier = Modifier, titleText: String) {
    Row (
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = titleText,
            fontFamily = PBCNameFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 48.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun TitleWithAction(modifier: Modifier = Modifier, titleText: String, icon: Painter, iconAction: (() -> Unit)? = null) {
    Row (
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = titleText,
            fontFamily = PBCNameFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 48.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = icon,
            contentDescription = "Dhamma chakra icon",
            contentScale = ContentScale.Companion.Crop,
            modifier = Modifier.Companion
                .size(56.dp)
                .clip(CircleShape)
                .clickable {
                    iconAction?.invoke()
                }
        )
    }
}

@Composable
fun TitleWithProfile(modifier: Modifier = Modifier, titleText: String, profilePicUrl: String, profileAction: () -> Unit) {
    Row (
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = titleText,
            fontFamily = PBCNameFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 48.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.weight(1f))

        Box (
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(
                    border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.tertiary),
                    shape = CircleShape
                )
                .clickable {
                    profileAction.invoke()
                }
        ) {
            AsyncImage(
                model = profilePicUrl,
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .padding(5.dp)
                    .clip(CircleShape)
            )
        }
    }
}

@Preview
@Composable
fun Title_Preview() {
    Column {
        TitleWithAction(
            titleText = "Sample Title",
            icon = painterResource(R.drawable.icon_pbc),
            iconAction = {})
        Title(titleText = "Sample Title")
        TitleWithProfile(titleText = "", profilePicUrl = "", profileAction = {})
    }
}