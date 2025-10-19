package com.kavi.pbc.droid.event.ui.create

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kavi.pbc.droid.event.R
import com.kavi.pbc.droid.lib.common.ui.component.Title
import javax.inject.Inject

class EventCreate @Inject constructor() {

    @Composable
    fun EventCreateUI(navController: NavController, modifier: Modifier = Modifier) {
        var selectedPagerIndex by rememberSaveable { mutableIntStateOf(0) }
        val state = rememberPagerState { 2 }

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
                    titleText = stringResource(R.string.label_create_event),
                )

                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 12.dp, end = 12.dp, top = 20.dp, bottom = 30.dp)
                ) {
                    HorizontalPager(
                        state = state,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 25.dp),
                        contentPadding = PaddingValues(horizontal = 0.dp),
                        snapPosition = SnapPosition.Center
                    ) { page ->
                        when (page) {
                            //0 -> eventPager.UpcomingEventPager(navController = navController)
                            //1 -> eventPager.PastEventPager(navController = navController)
                        }
                    }
                }
            }
        }
    }
}