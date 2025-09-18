package com.kavi.pbc.droid.dashboard.ui.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kavi.pbc.droid.dashboard.R
import com.kavi.pbc.droid.dashboard.data.repository.local.DashboardLocalRepository
import com.kavi.pbc.droid.lib.common.ui.component.TitleWithAction
import com.kavi.pbc.droid.lib.common.ui.component.TitleWithProfile
import com.kavi.pbc.droid.lib.common.ui.component.event.EventItem
import com.kavi.pbc.droid.network.session.Session
import kotlinx.coroutines.delay
import javax.inject.Inject

class Home @Inject constructor(
    private val dashboardLocalDataSource: DashboardLocalRepository
) {
    @Composable
    fun HomeUI(navController: NavController, modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()) {

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
                        titleText = stringResource(R.string.label_pbc),
                        profilePicUrl = it,
                        profileAction = {
                            navController.navigate("dashboard/to/profile")
                        }
                    )
                }?: run {
                    TitleWithAction(
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                        titleText = stringResource(R.string.label_pbc),
                        icon = painterResource(com.kavi.pbc.droid.lib.common.ui.R.drawable.image_dhamma_chakra),
                        iconAction = {}
                    )
                }

                Column (
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                ) {
                    // Daily Quotes
                    DailyQuoteAnimatorComponent(maxHeight = maxHeight, viewModel = viewModel)

                    // Event Pager
                    EventPager(viewModel = viewModel, navController = navController)
                }
            }
        }
    }

    @Composable
    private fun DailyQuoteAnimatorComponent(maxHeight: Dp, viewModel: HomeViewModel,
                                            intervalMillis: Long = 10000L) {
        var currentIndex by remember { mutableIntStateOf(0) }
        var visible by remember { mutableStateOf(true) }
        val dashboardQuoteList by viewModel.dashboardQuoteList.collectAsState()
        val imageList = listOf(
            R.drawable.image_lotus, R.drawable.image_buddha, R.drawable.image_pagoda
        )

        LaunchedEffect(Unit) {
            viewModel.getDashboardQuotes()
        }

        if (dashboardQuoteList.isNotEmpty()) {
            // Auto-cycle through the list
            LaunchedEffect(currentIndex) {
                visible = true
                delay(intervalMillis)
                visible = false
                delay(500) // wait for exit animation
                currentIndex = (currentIndex + 1) % dashboardQuoteList.size
            }

            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .height(maxHeight/8)
                    .padding(top = 20.dp, start = 12.dp, end = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column {
                        Row (
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(60.dp),
                                painter = painterResource(imageList[currentIndex]),
                                contentDescription = "Lotus image"
                            )

                            Text(
                                modifier = Modifier
                                    .padding(start = 8.dp),
                                text = dashboardQuoteList[currentIndex].quote,
                                textAlign = TextAlign.Justify,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )
                        }

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "-- ${dashboardQuoteList[currentIndex].author}",
                            textAlign = TextAlign.End,
                            fontStyle = FontStyle.Italic,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun EventPager(viewModel: HomeViewModel, navController: NavController) {
        val dashboardEvents by viewModel.dashboardEventList.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.fetchDashboardHomeEvents()
        }

        val state = rememberPagerState { 3 }

        Column {
            HorizontalPager(
                state = state,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp),
                contentPadding = PaddingValues(horizontal = 0.dp),
                snapPosition = SnapPosition.Center
            ) { page ->
                if (dashboardEvents.isNotEmpty())
                    EventItem(
                        event = dashboardEvents[page],
                        modifier = Modifier.clickable {
                            val tempEventKey = dashboardLocalDataSource.setSelectedEvent(dashboardEvents[page])
                            navController.navigate("dashboard/to/event/$tempEventKey")
                        }
                    )
            }

            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(state.pageCount) { iteration ->
                    val color = if (state.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(12.dp)
                    )
                }
            }
        }
    }
}