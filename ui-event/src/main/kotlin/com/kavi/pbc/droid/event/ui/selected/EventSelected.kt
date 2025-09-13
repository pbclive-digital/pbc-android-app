package com.kavi.pbc.droid.event.ui.selected

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kavi.droid.color.palette.extension.shadow
import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.data.dto.event.VenueType
import com.kavi.pbc.droid.event.R
import com.kavi.pbc.droid.lib.common.ui.component.AppButtonWithIcon
import com.kavi.pbc.droid.lib.common.ui.component.Title
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import javax.inject.Inject

class EventSelected @Inject constructor() {

    @Inject
    lateinit var eventPotluckSheet: EventPotluckSheet

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun EventUI(navController: NavController, eventData: Event? = null) {

        val potluckSheetState = rememberModalBottomSheetState()
        val showPotluckSheet = remember { mutableStateOf(false) }

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
                Title(
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                    titleText = stringResource(R.string.label_event),
                )

                eventData?.let { givenEvent ->
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
                            Text(
                                text = "On:",
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

                        Row (
                            modifier = Modifier.padding(top = 12.dp),
                        ) {
                            if (givenEvent.venueType == VenueType.PHYSICAL) {
                                Text(
                                    text = "At:",
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
                            } else {
                                Text(
                                    text = givenEvent.getPlace(),
                                    fontFamily = PBCFontFamily,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }

                        if (givenEvent.isRegistrationRequired) {
                            AppButtonWithIcon(
                                modifier = Modifier.padding(top = 12.dp),
                                label = stringResource(R.string.label_register),
                                icon = painterResource(R.drawable.icon_event_register)
                            ) {

                            }
                        }

                        if (givenEvent.isPotluckAvailable) {
                            AppButtonWithIcon(
                                modifier = Modifier.padding(top = 12.dp),
                                label = stringResource(R.string.label_potluck),
                                icon = painterResource(R.drawable.icon_potluck_register)
                            ) {
                                showPotluckSheet.value = true
                            }
                        }
                    }
                }
            }

            if (showPotluckSheet.value) {
                eventPotluckSheet.PotluckSheetUI(sheetState = potluckSheetState, showSheet = showPotluckSheet)
            }
        }
    }
}