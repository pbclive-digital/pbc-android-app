package com.kavi.pbc.droid.lib.common.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.kavi.pbc.droid.lib.common.ui.R
import com.kavi.pbc.droid.lib.common.ui.theme.PBCNameFontFamily

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

@Preview
@Composable
fun Title_Preview() {
    TitleWithAction(titleText = "Sample Title", icon = painterResource(R.drawable.icon_pbc), iconAction = {})
}