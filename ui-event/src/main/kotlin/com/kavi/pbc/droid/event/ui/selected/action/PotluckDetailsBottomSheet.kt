package com.kavi.pbc.droid.event.ui.selected.action

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kavi.droid.color.palette.extension.shadow
import com.kavi.pbc.droid.event.R
import com.kavi.pbc.droid.event.ui.common.EventPotluckItemUI
import com.kavi.pbc.droid.event.ui.selected.EventSelectedViewModel
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import com.kavi.pbc.droid.network.session.Session
import javax.inject.Inject

class PotluckDetailsBottomSheet @Inject constructor() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PotluckSheetUI(sheetState: SheetState, showSheet: MutableState<Boolean>,
                       viewModel: EventSelectedViewModel = hiltViewModel()
    ) {

        val eventPotluckData by viewModel.eventPotluckData.collectAsState()

        val potluckItemCount = eventPotluckData.potluckItemList.size

        val lazyColumHeight = if (potluckItemCount <= 3) {
            400.dp
        } else if(potluckItemCount in 4..6) {
            500.dp
        } else {
            600.dp
        }

        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                showSheet.value = false
            },
            containerColor = MaterialTheme.colorScheme.background,
            scrimColor = MaterialTheme.colorScheme.shadow.copy(alpha = .5f)
        ) {
            Box(
                modifier = Modifier.Companion
                    .background(MaterialTheme.colorScheme.background)
                    .padding(start = 20.dp, end = 20.dp, bottom = 40.dp)
                    .fillMaxWidth()
            ) {
                if (Session.isLogIn()) {
                    Column {
                        Text(
                            text = stringResource(R.string.label_event_contribute_potluck),
                            fontFamily = PBCFontFamily,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Companion.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.Companion
                                .fillMaxWidth()
                        )

                        HorizontalDivider(
                            modifier = Modifier.Companion.padding(2.dp),
                            thickness = 2.dp
                        )

                        Text(
                            text = stringResource(R.string.phrase_event_contribute_potluck),
                            fontFamily = PBCFontFamily,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Companion.Justify,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.Companion
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )

                        LazyColumn(
                            modifier = Modifier.Companion
                                .padding(top = 12.dp)
                                .height(lazyColumHeight)
                        ) {
                            items(eventPotluckData.potluckItemList) { potluckItem ->
                                EventPotluckItemUI(
                                    modifier = Modifier.Companion.padding(bottom = 8.dp),
                                    viewModel = viewModel,
                                    potluckItem = potluckItem,
                                    currentUserContributions = viewModel
                                        .checkedCurrentUserContribution(potluckItem = potluckItem)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}