package com.kavi.pbc.droid.event.ui.create

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kavi.pbc.droid.event.R
import com.kavi.pbc.droid.event.ui.create.common.NavigatorPanel
import com.kavi.pbc.droid.event.ui.create.pager.EventImageInformation
import com.kavi.pbc.droid.event.ui.create.pager.InitialInformation
import com.kavi.pbc.droid.event.ui.create.pager.SecondaryInformation
import com.kavi.pbc.droid.lib.common.ui.component.Title
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventCreate @Inject constructor() {

    @Inject
    lateinit var initialInformation: InitialInformation

    @Inject
    lateinit var secondaryInformation: SecondaryInformation

    @Inject
    lateinit var eventImageInformation: EventImageInformation

    @Composable
    fun EventCreateUI(navController: NavController, modifier: Modifier = Modifier, viewModel: EventCreateViewModel = hiltViewModel()) {

        val pageCount = 3
        val pagerState = rememberPagerState(pageCount = { pageCount })
        val scope = rememberCoroutineScope()

        var hidePrev by remember { mutableStateOf(false) }
        var hideNext by remember { mutableStateOf(false) }

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
                        .padding(top = 12.dp, bottom = 30.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth(),
                        userScrollEnabled = false,
                        contentPadding = PaddingValues(horizontal = 0.dp),
                        snapPosition = SnapPosition.Center
                    ) { page ->
                        when (page) {
                            0 -> {
                                initialInformation.InitialInformationUI()
                            }
                            1 -> {
                                secondaryInformation.SecondaryInformationUI()
                            }
                            2 -> {
                                eventImageInformation.EventImageUI()
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    NavigatorPanel(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, start = 4.dp, end = 4.dp, bottom = 8.dp),
                        hidePrev = hidePrev,
                        hideNext = hideNext,
                        onPrevious = {
                            scope.launch {
                                if (pagerState.currentPage != 0) {
                                    val prevPage = (pagerState.currentPage - 1 + pageCount) % pageCount
                                    pagerState.animateScrollToPage(prevPage)
                                }
                            }
                        },
                        onNext = {
                            scope.launch {
                                if (pagerState.currentPage != (pageCount - 1)) {
                                    val nextPage = (pagerState.currentPage + 1) % pageCount
                                    pagerState.animateScrollToPage(nextPage)
                                }

                                println("Event: ${viewModel.newEvent.value}")
                            }
                        }
                    )
                }
            }
        }
    }
}