package com.kavi.pbc.droid.temple.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.droid.color.palette.extension.shadow
import com.kavi.pbc.droid.lib.common.ui.component.AppButtonWithIcon
import com.kavi.pbc.droid.lib.common.ui.component.AppIconButton
import com.kavi.pbc.droid.lib.common.ui.component.Title
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import com.kavi.pbc.droid.temple.R
import javax.inject.Inject

class TempleContactUs @Inject constructor() {

    @Composable
    fun ContactUsUI() {

        val context: Context = LocalContext.current

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
                    titleText = stringResource(R.string.label_heading_contact_us),
                )

                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 30.dp)
                        .verticalScroll(state = rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.image_monk),
                        contentDescription = "Contact us icon",
                        modifier = Modifier
                            .size(350.dp)
                            .clip(CircleShape)
                    )

                    AppButtonWithIcon(
                        modifier = Modifier.padding(top = 12.dp),
                        label = stringResource(R.string.label_call_us),
                        icon = painterResource(R.drawable.icon_call)
                    ) {
                        // Open Dialer to call to the number
                    }

                    AppButtonWithIcon(
                        modifier = Modifier
                            .padding(top = 12.dp),
                        buttonHeight = 60.dp,
                        labelTextSize = 12.sp,
                        label = stringResource(R.string.label_email_us),
                        icon = painterResource(R.drawable.icon_email)
                    ) {
                        composeEmail(context = context)
                    }

                    Text(
                        text = stringResource(R.string.label_visit_us),
                        fontFamily = PBCFontFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .fillMaxWidth()
                    )

                    HorizontalDivider(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .fillMaxWidth(),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.shadow
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(R.string.label_temple_address),
                            fontFamily = PBCFontFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .padding(end = 20.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        AppIconButton(
                            icon = painterResource(R.drawable.icon_location),
                            buttonSize = 50.dp
                        ) {
                            openGoogleMaps(context = context)
                        }
                    }

                    Text(
                        text = stringResource(R.string.label_follow_us),
                        fontFamily = PBCFontFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .fillMaxWidth()
                    )

                    HorizontalDivider(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .fillMaxWidth(),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.shadow
                    )

                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.icon_facebook),
                            contentDescription = "Facebook icon",
                            modifier = Modifier
                                .size(80.dp)
                                .padding(8.dp)
                                .clickable {
                                    openLink("https://www.facebook.com/PittsburghBuddhistCenter", context)
                                }
                        )

                        Image(
                            painter = painterResource(R.drawable.icon_youtube),
                            contentDescription = "Youtube icon",
                            modifier = Modifier
                                .size(80.dp)
                                .padding(8.dp)
                                .clickable {
                                    openLink("https://www.youtube.com/pbclive", context)
                                }
                        )
                    }
                }
            }
        }
    }

    private fun openLink(url: String, context: Context) {
        val launchIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(launchIntent)
    }

    private fun openGoogleMaps(context: Context) {
        val address = "58 QSI Lane, Allison Park, PA 15101"
        val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(address)}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        context.startActivity(mapIntent)
    }

    private fun composeEmail(context: Context) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.setType("text/plain")
        val recipients = arrayOf(
            "info@pittsburghbuddhistcenter.org"
        )
        emailIntent.putExtra(Intent.EXTRA_EMAIL, recipients)
        emailIntent.setPackage("com.google.android.gm")

        context.startActivity(emailIntent)
    }
}