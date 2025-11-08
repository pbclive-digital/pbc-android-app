package com.kavi.pbc.droid.dashboard.ui.temple

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kavi.pbc.droid.dashboard.R
import com.kavi.pbc.droid.data.dto.user.UserType
import com.kavi.pbc.droid.lib.common.ui.component.TitleWithAction
import com.kavi.pbc.droid.lib.common.ui.component.TitleWithProfile
import com.kavi.pbc.droid.lib.common.ui.theme.BottomNavBarHeight
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import com.kavi.pbc.droid.network.session.Session
import javax.inject.Inject

@Suppress("COMPOSE_APPLIER_CALL_MISMATCH")
class Temple @Inject constructor() {

    @Composable
    fun TempleUI(navController: NavController, modifier: Modifier = Modifier) {

        BoxWithConstraints (
            modifier = Modifier
                .padding(top = 56.dp, start = 16.dp, end = 16.dp)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            val maxHeight = this.maxHeight
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Session.user?.profilePicUrl?.let {
                    TitleWithProfile(
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                        titleText = stringResource(R.string.label_temple),
                        profilePicUrl = it,
                        profileAction = {
                            navController.navigate("dashboard/to/profile")
                        }
                    )
                }?: run {
                    TitleWithAction(
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                        titleText = stringResource(R.string.label_temple),
                        icon = painterResource(com.kavi.pbc.droid.lib.common.ui.R.drawable.image_dhamma_chakra),
                        iconAction = {}
                    )
                }

                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(maxHeight - BottomNavBarHeight - 45.dp)
                        .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 20.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = stringResource(R.string.label_service),
                        fontFamily = PBCFontFamily,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            top = 12.dp
                        )
                    )

                    Card (
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                    ) {
                        Column (
                            modifier = Modifier.padding(12.dp)
                        ) {
                            OptionItem(label = stringResource(R.string.label_ask_question)) {

                            }
                            OptionItem(label = stringResource(R.string.label_make_appointment)) {
                                navController.navigate("dashboard/to/appointment")
                            }
                            OptionItem(label = stringResource(R.string.label_donate)) {

                            }
                            OptionItem(label = stringResource(R.string.label_contact_us)) {
                                navController.navigate("dashboard/to/temple/contact-us")
                            }
                            OptionItem(label = stringResource(R.string.label_about_us), isWithDivider = false) {
                                navController.navigate("dashboard/to/temple/about-us")
                            }
                        }
                    }

                    if (Session.user?.userType == UserType.ADMIN) {
                        Text(
                            text = stringResource(R.string.label_admin),
                            fontFamily = PBCFontFamily,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(
                                top = 25.dp
                            )
                        )

                        Card (
                            modifier = Modifier
                                .padding(top = 12.dp, bottom = 20.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                        ) {
                            Column (
                                modifier = Modifier.padding(12.dp)
                            ) {
                                OptionItem(label = stringResource(R.string.label_manage_event)) {
                                    navController.navigate("dashboard/admin/to/event/manage-event")
                                }
                                OptionItem(label = stringResource(R.string.label_manage_user)) {
                                    navController.navigate("dashboard/admin/to/user")
                                }
                                OptionItem(label = stringResource(R.string.label_create_news)) {

                                }
                                OptionItem(label = stringResource(R.string.label_message_broadcast), isWithDivider = false) {

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun OptionItem(label: String, isWithDivider: Boolean = true, action: () -> Unit) {
        Row (
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .clickable {
                    action.invoke()
                }
        ) {
            Text(
                text = label,
                fontFamily = PBCFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "KeyboardArrowRight")
        }
        if (isWithDivider)
            HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, start = 8.dp, end = 8.dp))
    }
}
