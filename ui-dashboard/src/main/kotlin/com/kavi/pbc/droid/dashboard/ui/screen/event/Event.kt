package com.kavi.pbc.droid.dashboard.ui.screen.event

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kavi.droid.color.palette.extension.quaternary
import com.kavi.pbc.droid.dashboard.R
import com.kavi.pbc.droid.dashboard.ui.screen.event.pager.EventPager
import com.kavi.pbc.droid.lib.common.ui.component.TitleWithAction
import javax.inject.Inject

class Event @Inject constructor() {

    @Inject
    lateinit var eventPager: EventPager

    @Composable
    fun EventUI(navController: NavHostController, modifier: Modifier = Modifier) {

        var selectedPagerIndex by rememberSaveable { mutableIntStateOf(0) }
        val state = rememberPagerState { 2 }

        LaunchedEffect(selectedPagerIndex) {
            state.animateScrollToPage(selectedPagerIndex)
        }

        BoxWithConstraints(
            modifier = Modifier
                .padding(top = 56.dp, start = 16.dp, end = 16.dp)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            val screenWidth = this.maxWidth
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TitleWithAction(
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                    titleText = stringResource(R.string.label_event),
                    icon = painterResource(com.kavi.pbc.droid.lib.common.ui.R.drawable.image_dhamma_chakra),
                    iconAction = {}
                )

                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, start = 12.dp, end = 12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    selectedPagerIndex = 0
                                },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Upcoming")
                        }

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    selectedPagerIndex = 1
                                },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Past")
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 12.dp)
                    ) {
                        repeat(state.pageCount) { iteration ->
                            //selectedPagerIndex = state.currentPage
                            //viewModel.setOrUpdateSelectedPageIndex(state.currentPage)
                            val color = if (state.currentPage == iteration)
                                MaterialTheme.colorScheme.quaternary else MaterialTheme.colorScheme.surface

                            Box(
                                modifier = Modifier
                                    .padding(top = 12.dp)
                                    .height(2.dp)
                                    .width((screenWidth - 20.dp) / 2)
                                    .fillMaxWidth()
                                    .background(color)
                            )
                        }
                    }
                }

                HorizontalPager(
                    state = state,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp),
                    contentPadding = PaddingValues(horizontal = 0.dp),
                    snapPosition = SnapPosition.Center
                ) { page ->
                    when (page) {
                        0 -> eventPager.UpcomingEventPager()
                        1 -> eventPager.PastEventPager()
                    }
                }
            }
        }
    }
}