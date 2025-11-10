package com.kavi.pbc.droid.user.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.droid.color.palette.extension.shadow
import com.kavi.pbc.droid.data.dto.user.User
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import com.kavi.pbc.droid.user.R

@Suppress("COMPOSE_APPLIER_CALL_MISMATCH")
@Composable
fun UserListItem(modifier: Modifier = Modifier, user: User, onView: () -> Unit, onModify: () -> Unit) {
    BoxWithConstraints (
        modifier = modifier.padding(top = 2.dp)
    ) {
        val screenWidth = this.maxWidth

        Row (
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .border(1.dp, MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(8.dp))
                .shadow(
                    elevation = 8.dp,
                    spotColor = MaterialTheme.colorScheme.shadow,
                    shape = RoundedCornerShape(8.dp),
                )
                .background(MaterialTheme.colorScheme.background),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (
                modifier = modifier
                    .padding(12.dp)
            ) {
                Text(
                    text = "${user.firstName} ${user.lastName}",
                    fontFamily = PBCFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Row (
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Column {
                        Text(
                            modifier = Modifier
                                .width((screenWidth.value * 0.70).dp),
                            text = user.email,
                            fontFamily = PBCFontFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            modifier = Modifier.padding(top = 4.dp),
                            text = "Role: ${user.userType.name}",
                            fontFamily = PBCFontFamily,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    /*AsyncImage(
                        model = user.profilePicUrl,
                        error = painterResource(R.drawable.icon_user_default_pic),
                        contentDescription = "Event image picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(75.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )*/
                    Row(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .width((screenWidth.value * 0.70).dp),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Icon(
                            painter = painterResource(com.kavi.pbc.droid.lib.common.ui.R.drawable.icon_view),
                            contentDescription = "Edit Event",
                            tint = MaterialTheme.colorScheme.shadow,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(4.dp)
                                .clickable {
                                    onView.invoke()
                                }
                        )

                        Icon(
                            painter = painterResource(com.kavi.pbc.droid.lib.common.ui.R.drawable.icon_edit),
                            contentDescription = "Edit Event",
                            tint = MaterialTheme.colorScheme.shadow,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(4.dp)
                                .clickable {
                                    onModify.invoke()
                                }
                        )
                    }
                }
            }
        }
    }
}

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

                Text(
                    text = profileUser.email,
                    fontFamily = PBCFontFamily,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.End,
                    fontSize = 18.sp,
                )
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

                Text(
                    text = profileUser.phoneNumber ?: run { "" },
                    fontFamily = PBCFontFamily,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.End,
                    fontSize = 18.sp,
                )
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

private fun getContactDetails(user: User): String? {
    return if (user.phoneNumber != null && user.address != null) {
        user.phoneNumber
    } else if (user.phoneNumber != null && user.address.isNullOrEmpty()) {
        user.phoneNumber
    } else if (user.phoneNumber.isNullOrEmpty() && user.address != null) {
        user.address
    } else {
        null
    }
}

@Preview
@Composable
fun Preview_EventListItem() {
    UserListItem(user = User(
        id = "evt:YgY:1754372246218",
        email = "test@gmail.com",
        firstName = "Test",
        lastName = "Value",
        //phoneNumber = "4123209979",
        address = "8505, Venego road asdasd asdasd, Pittsburgh PA 15212"
    ), onModify = {}, onView = {})
}