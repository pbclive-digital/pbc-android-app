package com.kavi.pbc.droid.event.ui.create

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kavi.pbc.droid.event.R
import com.kavi.pbc.droid.event.ui.create.common.NavigatorPanel
import com.kavi.pbc.droid.event.ui.create.pager.SignUpSheetAndImagePager
import com.kavi.pbc.droid.event.ui.create.pager.InformationSheetPager
import com.kavi.pbc.droid.event.ui.create.pager.PotluckAndRegistrationPager
import com.kavi.pbc.droid.lib.common.ui.component.AppFullScreenLoader
import com.kavi.pbc.droid.lib.common.ui.component.Title
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventCreateOrModify @Inject constructor() {

    @Inject
    lateinit var informationSheetPager: InformationSheetPager

    @Inject
    lateinit var potluckAndRegistrationPager: PotluckAndRegistrationPager

    @Inject
    lateinit var signUpSheetAndImagePager: SignUpSheetAndImagePager

    @Composable
    fun EventCreateOrModifyUI(navController: NavController,
                              modifyingEventKey: String? = null,
                              modifier: Modifier = Modifier,
                              viewModel: EventCreateViewModel = hiltViewModel()) {

        val pageCount = 3
        val pagerState = rememberPagerState(pageCount = { pageCount })
        val scope = rememberCoroutineScope()

        var hidePrev by remember { mutableStateOf(false) }
        var hideNext by remember { mutableStateOf(false) }
        var makeFinish by remember { mutableStateOf(false) }
        var isModify by remember { mutableStateOf(false) }

        val isLoading = remember { mutableStateOf(false) }
        val eventCreateOrUpdateStatus by viewModel.eventCreateOrUpdateStatus.collectAsState()

        val context = LocalContext.current

        LaunchedEffect(pagerState.currentPage) {
            when(pagerState.currentPage) {
                0 -> {
                    hidePrev = true
                    hideNext = false
                    makeFinish = false
                }
                1 -> {
                    hidePrev = false
                    hideNext = false
                    makeFinish = false
                }
                2 -> {
                    hidePrev = false
                    hideNext = true
                    makeFinish = true
                }
            }
        }

        modifyingEventKey?.let {
            isModify = true
            viewModel.setModifyingEvent(eventKey = modifyingEventKey)
        }

        Box {
            Box(
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
                        titleText = if (isModify) stringResource(R.string.label_modify_event) else stringResource(
                            R.string.label_create_event
                        ),
                    )

                    Column(
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
                                    informationSheetPager.InformationSheetUI()
                                }

                                1 -> {
                                    potluckAndRegistrationPager.PotluckAndRegistrationUI()
                                }

                                2 -> {
                                    signUpSheetAndImagePager.SignUpSheetAndImageUI()
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
                            makeFinish = makeFinish,
                            isModify = isModify,
                            onPrevious = {
                                scope.launch {
                                    if (pagerState.currentPage != 0) {
                                        val prevPage =
                                            (pagerState.currentPage - 1 + pageCount) % pageCount
                                        pagerState.animateScrollToPage(prevPage)
                                    }
                                }
                            },
                            onNext = {
                                scope.launch {
                                    if (pagerState.currentPage != (pageCount - 1)) {
                                        var gotoNext = false
                                        when (pagerState.currentPage) {
                                            0 -> {
                                                gotoNext = viewModel.validateFirstPage()
                                            }

                                            1 -> {
                                                gotoNext = viewModel.validateSecondPage()
                                            }

                                            2 -> gotoNext = true
                                        }

                                        if (!makeFinish) {
                                            if (gotoNext) {
                                                val nextPage =
                                                    (pagerState.currentPage + 1) % pageCount
                                                pagerState.animateScrollToPage(nextPage)
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    context.getString(R.string.phrase_validation_failure_form_one),
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        } else {
                                            isLoading.value = true
                                            viewModel.uploadEventImageAndCreateOrUpdateEvent(
                                                isModify = isModify
                                            )
                                        }
                                    } else {
                                        if (makeFinish) {
                                            isLoading.value = true
                                            viewModel.uploadEventImageAndCreateOrUpdateEvent(
                                                isModify = isModify
                                            )
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            }

            if (isLoading.value) {
                AppFullScreenLoader()
            }
        }

        if (eventCreateOrUpdateStatus) {
            navController.popBackStack()
        }
    }
}