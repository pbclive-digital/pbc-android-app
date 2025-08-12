package com.kavi.pbc.droid.lib.common.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.pbc.droid.lib.common.ui.theme.GrayText

@Composable
fun AppBlueFilledButton(label: String,
                        labelTextSize: TextUnit? = null,
                        modifier: Modifier = Modifier,
                        onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(50.dp),
        shape = RoundedCornerShape(5.dp),
        //colors = ButtonDefaults.buttonColors(contentColor = white, containerColor = blueLight)
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
fun AppBlueOutlineButton(label: String,
                         labelTextSize: TextUnit? = null,
                         modifier: Modifier,
                         onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(50.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(5.dp),
        //colors = ButtonDefaults.outlinedButtonColors(contentColor = blueLight)
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

@Preview(showBackground = true)
@Composable
fun PreviewUi() {
    AppDisabledOutlineButton("Button Text", modifier = Modifier) {}
}