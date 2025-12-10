package com.kavi.pbc.droid.lib.common.ui.component.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.droid.color.palette.extension.shadow
import com.kavi.pbc.droid.data.dto.user.User
import com.kavi.pbc.droid.lib.common.ui.R
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily

@Composable
fun BasicUserInfoCard(profileUser: User) {
    Card(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = MaterialTheme.colorScheme.shadow
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column (
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.label_user_basic_info),
                fontFamily = PBCFontFamily,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                fontSize = 22.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = stringResource(R.string.phrase_user_basic_info),
                fontFamily = PBCFontFamily,
                fontWeight = FontWeight.Light,
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            )

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.label_user_email),
                    fontFamily = PBCFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    modifier = Modifier.width(100.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                SelectionContainer {
                    Text(
                        text = profileUser.email,
                        fontFamily = PBCFontFamily,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.End,
                        fontSize = 18.sp,
                    )
                }
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.label_user_phone),
                    fontFamily = PBCFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    modifier = Modifier.width(100.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                SelectionContainer {
                    Text(
                        text = profileUser.phoneNumber ?: run { "" },
                        fontFamily = PBCFontFamily,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.End,
                        fontSize = 18.sp,
                    )
                }
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.label_user_address),
                    fontFamily = PBCFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    modifier = Modifier.width(100.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                SelectionContainer {
                    Text(
                        text = profileUser.address ?: run { "" },
                        fontFamily = PBCFontFamily,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.End,
                        fontSize = 18.sp,
                    )
                }
            }
        }
    }
}