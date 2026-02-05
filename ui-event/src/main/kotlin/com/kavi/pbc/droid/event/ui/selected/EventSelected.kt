package com.kavi.pbc.droid.event.ui.selected

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kavi.droid.color.palette.extension.shadow
import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.data.dto.event.EventStatus
import com.kavi.pbc.droid.data.dto.event.VenueType
import com.kavi.pbc.droid.data.dto.event.signup.EventSignUpSheet
import com.kavi.pbc.droid.data.dto.user.UserType
import com.kavi.pbc.droid.event.R
import com.kavi.pbc.droid.event.ui.common.SignUpSheetItemUI
import com.kavi.pbc.droid.event.ui.selected.action.EventRegistrationSheet
import com.kavi.pbc.droid.event.ui.selected.action.PotluckDetailsBottomSheet
import com.kavi.pbc.droid.event.ui.selected.action.SignUpSheetRegistrationSheet
import com.kavi.pbc.droid.lib.common.ui.component.AppButtonWithIcon
import com.kavi.pbc.droid.lib.common.ui.component.AppIconButton
import com.kavi.pbc.droid.lib.common.ui.component.Title
import com.kavi.pbc.droid.lib.common.ui.component.TitleWithAction
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import com.kavi.pbc.droid.lib.parent.contract.ContractServiceLocator
import com.kavi.pbc.droid.lib.parent.contract.module.AuthContract
import com.kavi.pbc.droid.network.session.Session
import javax.inject.Inject

@Suppress("COMPOSE_APPLIER_CALL_MISMATCH")
class EventSelected @Inject constructor() {

    @Inject
    lateinit var potluckDetailsBottomSheet: PotluckDetailsBottomSheet
    @Inject
    lateinit var eventRegistrationAction: EventRegistrationSheet
    @Inject
    lateinit var signUpSheetRegistrationSheet: SignUpSheetRegistrationSheet

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun EventUI(navController: NavController, eventData: Event? = null, viewModel: EventSelectedViewModel = hiltViewModel()) {

        val context = LocalContext.current

        val authInviteSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
        val showAuthInviteSheet = remember { mutableStateOf(false) }

        val potluckSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
        val showPotluckSheet = remember { mutableStateOf(false) }

        val registrationSheetState = rememberModalBottomSheetState()
        val showRegistrationSheet = remember { mutableStateOf(false) }

        val signUpSheetBottomSheetState = rememberModalBottomSheetState()
        val showSignUpSheetBottomSheet = remember { mutableStateOf(false) }
        val selectedSignUpSheetItem = remember { mutableStateOf(EventSignUpSheet()) }

        eventData?.let {
            viewModel.setGivenEvent(givenEvent = eventData)
        }

        val givenEvent by viewModel.givenEvent.collectAsState()
        val eventSignUpSheetData by viewModel.eventSignUpSheetData.collectAsState()

        BoxWithConstraints (
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(top = 56.dp, start = 16.dp, end = 16.dp)
                .fillMaxSize()
        ) {
            val screenWith = this.maxWidth
            val screenHeight = this.maxHeight

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight)
            ) {
                Session.user?.let {
                    when(it.userType) {
                        UserType.ADMIN, UserType.MANAGER -> {
                            TitleWithAction(
                                modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                                titleText = stringResource(R.string.label_event),
                                actionPainter = painterResource(com.kavi.pbc.droid.lib.common.ui.R.drawable.icon_manage),
                                isIcon = true,
                                actionPainterSize = 40.dp
                            ) {
                                // Navigate to event manage screen
                            }
                        }
                        UserType.MONK -> {
                            if (it.residentMonk) {
                                TitleWithAction(
                                    modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                                    titleText = stringResource(R.string.label_event),
                                    actionPainter = painterResource(com.kavi.pbc.droid.lib.common.ui.R.drawable.icon_manage),
                                    actionPainterSize = 40.dp
                                ) {
                                    // Navigate to event manage screen
                                }
                            } else {
                                Title(
                                    modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                                    titleText = stringResource(R.string.label_event)
                                )
                            }
                        }
                        UserType.CONSUMER -> {
                            Title(
                                modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                                titleText = stringResource(R.string.label_event)
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding( start = 12.dp, end = 12.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(screenWith)
                            .padding(top = 20.dp)
                            .shadow(
                                elevation = 12.dp,
                                spotColor = MaterialTheme.colorScheme.shadow
                            ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        AsyncImage(
                            model = givenEvent.eventImage,
                            error = painterResource(com.kavi.pbc.droid.lib.common.ui.R.drawable.icon_pbc),
                            contentDescription = null, // decorative image
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = MaterialTheme.colorScheme.background)
                        )
                    }

                    Text(
                        modifier = Modifier.padding(top = 18.dp),
                        text = givenEvent.name,
                        fontFamily = PBCFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp,
                        lineHeight = 40.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        modifier = Modifier.padding(top = 12.dp),
                        text = givenEvent.description,
                        fontFamily = PBCFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Row (
                        modifier = Modifier.padding(top = 12.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                text = stringResource(R.string.label_on),
                                fontFamily = PBCFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = givenEvent.getFormatDate(),
                                fontFamily = PBCFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = stringResource(R.string.label_from),
                                fontFamily = PBCFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = "${givenEvent.startTime} - ${givenEvent.endTime}",
                                fontFamily = PBCFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }

                    Row (
                        modifier = Modifier.padding(top = 12.dp),
                    ) {
                        if (givenEvent.venueType == VenueType.PHYSICAL) {
                            Text(
                                text = stringResource(R.string.label_at),
                                fontFamily = PBCFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = givenEvent.getPlace(),
                                fontFamily = PBCFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            AppIconButton(
                                icon = painterResource(R.drawable.icon_location),
                                buttonSize = 40.dp
                            ) {
                                givenEvent.venueAddress?.let {
                                    openGoogleMaps(address = it, context = context)
                                }
                            }
                        } else {
                            Text(
                                text = givenEvent.getPlace(),
                                fontFamily = PBCFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            AppIconButton(
                                icon = painterResource(R.drawable.icon_online_meeting),
                                buttonSize = 40.dp
                            ) {
                                givenEvent.meetingUrl?.let {
                                    openMeetingLink(meetingUrl = it, context = context)
                                }
                            }
                        }
                    }

                    //Spacer(modifier = Modifier.weight(1f))

                    if (givenEvent.registrationRequired && givenEvent.eventStatus == EventStatus.PUBLISHED) {
                        var registrationBottomPadding = 0.dp
                        if (!givenEvent.potluckAvailable) {
                            registrationBottomPadding = 40.dp
                        }

                        Column(
                            modifier = Modifier.padding(top = 16.dp, bottom = registrationBottomPadding)
                        ) {
                            Text(
                                text = stringResource(R.string.label_event_registration),
                                fontFamily = PBCFontFamily,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )

                            HorizontalDivider(
                                modifier = Modifier.padding(2.dp),
                                thickness = 2.dp
                            )

                            Text(
                                text = stringResource(R.string.phrase_event_registration_details),
                                fontFamily = PBCFontFamily,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Justify,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )

                            AppButtonWithIcon(
                                modifier = Modifier.padding(top = 8.dp),
                                label = stringResource(R.string.label_register),
                                icon = painterResource(R.drawable.icon_event_register)
                            ) {
                                if (Session.isLogIn())
                                    showRegistrationSheet.value = true
                                else
                                    showAuthInviteSheet.value = true
                            }
                        }
                    }

                    if (givenEvent.potluckAvailable && givenEvent.eventStatus == EventStatus.PUBLISHED) {
                        var potluckBottomPadding = 0.dp
                        if (!givenEvent.signUpSheetAvailable) {
                            potluckBottomPadding = 40.dp
                        }

                        Column(
                            modifier = Modifier.padding(top = 20.dp, bottom = potluckBottomPadding)
                        ) {
                            Text(
                                text = stringResource(R.string.label_event_potluck),
                                fontFamily = PBCFontFamily,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )

                            HorizontalDivider(
                                modifier = Modifier.padding(2.dp),
                                thickness = 2.dp
                            )

                            Text(
                                text = stringResource(R.string.phrase_event_potluck_details),
                                fontFamily = PBCFontFamily,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Justify,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )

                            AppButtonWithIcon(
                                modifier = Modifier.padding(top = 8.dp),
                                label = stringResource(R.string.label_potluck),
                                icon = painterResource(R.drawable.icon_potluck_register)
                            ) {
                                if (Session.isLogIn())
                                    showPotluckSheet.value = true
                                else
                                    showAuthInviteSheet.value = true
                            }
                        }
                    }

                    if (givenEvent.signUpSheetAvailable && givenEvent.eventStatus == EventStatus.PUBLISHED) {
                        Column(
                            modifier = Modifier.padding(top = 20.dp, bottom = 40.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.label_event_additional_sign_ups),
                                fontFamily = PBCFontFamily,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )

                            HorizontalDivider(
                                modifier = Modifier.padding(2.dp),
                                thickness = 2.dp
                            )

                            Text(
                                text = stringResource(R.string.phrase_event_additional_sign_ups),
                                fontFamily = PBCFontFamily,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Justify,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )

                            eventSignUpSheetData.signUpSheetItemList.forEach { signUpSheetItem ->
                                Spacer(modifier = Modifier.height(8.dp))
                                SignUpSheetItemUI(
                                    modifier = Modifier
                                        .clickable {
                                            // Open up bottom sheet to register to selected sign-up sheet
                                            if (Session.isLogIn()) {
                                                selectedSignUpSheetItem.value = signUpSheetItem
                                                showSignUpSheetBottomSheet.value = true
                                            } else
                                                showAuthInviteSheet.value = true
                                        },
                                    signUpSheet = signUpSheetItem
                                )
                            }
                        }
                    }
                }
            }

            if (showPotluckSheet.value) {
                potluckDetailsBottomSheet.PotluckSheetUI(sheetState = potluckSheetState, showSheet = showPotluckSheet)
            }

            if (showRegistrationSheet.value) {
                eventRegistrationAction.RegistrationSheetUI(sheetState = registrationSheetState, showSheet = showRegistrationSheet)
            }

            if (showSignUpSheetBottomSheet.value) {
                signUpSheetRegistrationSheet.SignUpSheetBottomSheetUI(
                    sheetState = signUpSheetBottomSheetState,
                    showSheet = showSignUpSheetBottomSheet,
                    selectedSignUpSheet = selectedSignUpSheetItem.value
                )
            }

            if (showAuthInviteSheet.value) {
                ContractServiceLocator.locate(AuthContract::class).AuthInviteBottomSheet(
                    sheetState = authInviteSheetState, showSheet = showAuthInviteSheet
                ) {
                    showAuthInviteSheet.value = false
                    navController.navigate("event/to/auth")
                }
            }
        }
    }

    private fun openGoogleMaps(address: String, context: Context) {
        val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(address)}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        context.startActivity(mapIntent)
    }

    private fun openMeetingLink(meetingUrl: String, context: Context) {
        val launchIntent = Intent(Intent.ACTION_VIEW, Uri.parse(meetingUrl))
        context.startActivity(launchIntent)
    }
}