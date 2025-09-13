package com.kavi.pbc.droid.lib.common.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.pbc.droid.lib.common.ui.theme.GrayText

@Composable
fun AppFilledButton(label: String,
                    labelTextSize: TextUnit? = null,
                    modifier: Modifier = Modifier,
                    onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(50.dp),
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.onPrimary, containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = label.uppercase(),
            fontSize = labelTextSize ?: run { 14.sp },
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(4.dp),
        )
    }
}

@Composable
fun AppButtonWithIcon(label: String,
                      icon: Painter,
                      labelTextSize: TextUnit? = null,
                      modifier: Modifier = Modifier,
                      onClick: () -> Unit) {

    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(50.dp),
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.onPrimary, containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = icon,
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp)
            )
            Text(
                text = label.uppercase(),
                fontSize = labelTextSize ?: run { 14.sp },
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(4.dp),
            )
        }
    }
}

@Composable
fun AppDisabledFilledButton(label: String,
                            labelTextSize: TextUnit? = null,
                            modifier: Modifier,
                            onClick: (() -> Unit)? = null) {
    Button(
        onClick = onClick ?: run {{ /*Empty click event*/ }},
        modifier = modifier.fillMaxWidth().height(50.dp),
        shape = RoundedCornerShape(5.dp),
        //colors = ButtonDefaults.buttonColors(contentColor = grayText, containerColor = grayLight)
    ) {
        Text(
            text = label.uppercase(),
            fontSize = labelTextSize ?: run { 14.sp },
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(4.dp),
        )
    }
}

@Composable
fun AppOutlineButton(label: String,
                     labelTextSize: TextUnit? = null,
                     modifier: Modifier,
                     onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(50.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary,
        )
    ) {
        Text(
            text = label.uppercase(),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = labelTextSize ?: run { 14.sp },
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(4.dp),
        )
    }
}

@Composable
fun AppDisabledOutlineButton(label: String,
                             labelTextSize: TextUnit? = null,
                             modifier: Modifier,
                             onClick: (() -> Unit)? = null) {
    OutlinedButton(
        onClick = onClick ?: run {{ /*Empty click event*/ }},
        modifier = modifier.fillMaxWidth().height(50.dp),
        border = BorderStroke(1.dp, GrayText),
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = GrayText)
    ) {
        Text(
            text = label.uppercase(),
            fontSize = labelTextSize ?: run { 14.sp },
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(4.dp),
        )
    }
}

@Composable
fun AppLinkButton(
    label: String,
    labelTextSize: TextUnit? = null,
    fontFamily: FontFamily? = null,
    color: Color? = null,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Text(
        text = label,
        fontSize = labelTextSize ?: run { 16.sp },
        fontFamily = fontFamily,
        color = color ?: run { MaterialTheme.colorScheme.primary },
        style = TextStyle(textDecoration = TextDecoration.Underline),
        modifier = modifier
            .clickable {
                onClick.invoke()
            },
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewUi() {
    Column {
        AppDisabledOutlineButton("Button Text", modifier = Modifier) {}

        AppLinkButton("Button Text Link", ) {}
    }
}