package com.kavi.pbc.droid.temple.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.droid.color.palette.extension.quaternary
import com.kavi.pbc.droid.lib.common.ui.component.Title
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import com.kavi.pbc.droid.temple.R
import javax.inject.Inject

class TempleAboutUs @Inject constructor() {

    @Composable
    fun AboutUsUI() {
        Box (
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(top = 56.dp, start = 16.dp, end = 16.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Title(
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                    titleText = stringResource(R.string.label_heading_about_us),
                )

                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 30.dp)
                        .verticalScroll(state = rememberScrollState())
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(com.kavi.pbc.droid.lib.common.ui.R.drawable.icon_pbc),
                            contentDescription = "Pittsburgh Buddhist Center icon",
                            modifier = Modifier
                                .size(200.dp)
                                .clip(CircleShape)
                        )

                        Text(
                            stringResource(com.kavi.pbc.droid.lib.common.ui.R.string.label_pbc_part_one),
                            fontFamily = PBCFontFamily,
                            fontSize = 28.sp,
                            color = MaterialTheme.colorScheme.quaternary,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        Text(
                            stringResource(com.kavi.pbc.droid.lib.common.ui.R.string.label_pbc_part_two),
                            fontFamily = PBCFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Text(
                        text = stringResource(R.string.phrase_about_us),
                        fontFamily = PBCFontFamily,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    )
                }
            }
        }
    }
}