package com.kavi.pbc.droid.event.ui.create.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.droid.color.palette.extension.quaternary
import com.kavi.pbc.droid.event.R
import com.kavi.pbc.droid.lib.common.ui.component.AppFilledButton

@Composable
fun NavigatorPanel(
    modifier: Modifier = Modifier,
    hidePrev: Boolean = false,
    onPrevious: () -> Unit,
    hideNext: Boolean = false,
    makeFinish: Boolean = false,
    onNext: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!hidePrev) {
            OutlinedButton(
                modifier = Modifier
                    .width(120.dp)
                    .height(50.dp),
                onClick = onPrevious,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.quaternary,
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.AutoMirrored.Filled.KeyboardArrowLeft),
                        contentDescription = "",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(4.dp)
                    )
                    Text(
                        text = stringResource(R.string.label_previous),
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(
                            top = 6.dp,
                            start = 2.dp,
                            bottom = 4.dp,
                            end = 4.dp
                        ),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        if (!hideNext && !makeFinish) {
            Button(
                onClick = onNext,
                modifier = Modifier
                    .width(120.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.label_next),
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(4.dp),
                    )
                    Icon(
                        painter = rememberVectorPainter(image = Icons.AutoMirrored.Filled.KeyboardArrowRight),
                        contentDescription = "",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(4.dp)
                    )
                }
            }
        }

        if (makeFinish) {
            AppFilledButton(
                label = stringResource(R.string.label_submit),
                modifier = Modifier
                    .width(120.dp)
                    .height(50.dp)
            ) {
                onNext.invoke()
            }
        }
    }
}