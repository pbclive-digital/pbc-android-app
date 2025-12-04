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
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kavi.droid.color.palette.extension.inverseDefault
import com.kavi.droid.color.palette.extension.shadow
import com.kavi.pbc.droid.lib.common.ui.R
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily

@Composable
fun Title(modifier: Modifier = Modifier, titleText: String) {
    Row (
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = titleText,
            fontFamily = PBCFontFamily,
            fontWeight = FontWeight.Bold,
            lineHeight = 48.sp,
            fontSize = 48.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun TitleWithAction(modifier: Modifier = Modifier,
                    titleText: String,
                    actionPainter: Painter,
                    actionPainterSize: Dp = 56.dp,
                    isIcon: Boolean = false,
                    action: (() -> Unit)? = null) {
    Row (
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = titleText,
            fontFamily = PBCFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 48.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.weight(1f))
        if (isIcon) {
            Icon(
                painter = actionPainter,
                contentDescription = "Provided icon",
                tint = MaterialTheme.colorScheme.shadow,
                modifier = Modifier
                    .size(actionPainterSize)
                    .clickable {
                        action?.invoke()
                    }
            )
        } else {
            Image(
                painter = actionPainter,
                contentDescription = "provided image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(actionPainterSize)
                    .clickable {
                        action?.invoke()
                    }
            )
        }
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
            fontFamily = PBCFontFamily,
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
            actionPainter = painterResource(R.drawable.icon_pbc),
            action = {})
        Title(titleText = "Sample Title")
        TitleWithProfile(titleText = "", profilePicUrl = "", profileAction = {})
    }
}