package com.kavi.pbc.droid.ask.question.ui.manage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kavi.droid.color.palette.extension.quaternary
import com.kavi.pbc.droid.ask.question.R
import com.kavi.pbc.droid.lib.common.ui.component.AppButtonWithIcon
import com.kavi.pbc.droid.lib.common.ui.component.Title
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import com.kavi.pbc.droid.network.session.Session
import javax.inject.Inject

class QuestionManage @Inject constructor() {

    @Suppress("COMPOSE_APPLIER_CALL_MISMATCH")
    @Composable
    fun QuestionManageUI(navController: NavController, viewModel: QuestionManageViewModel = hiltViewModel()) {
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            viewModel.fetchAllQuestionList()
            viewModel.fetchUserQuestionList()
        }

        Box {
            BoxWithConstraints(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(top = 56.dp, start = 16.dp, end = 16.dp)
                    .fillMaxSize()
            ) {
                val screenHeight = this.maxHeight
                val screenWidth = this.maxWidth

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Title(
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                        titleText = stringResource(R.string.label_question_asked),
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 30.dp)
                            .verticalScroll(state = rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = stringResource(R.string.phrase_question_asked),
                            fontFamily = PBCFontFamily,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Justify,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        Session.user?.let { user ->
                            if (!user.residentMonk) {
                                AppButtonWithIcon(
                                    modifier = Modifier.padding(top = 12.dp),
                                    label = stringResource(R.string.label_ask_your_question),
                                    icon = painterResource(R.drawable.icon_ask_question)
                                ) {
                                    navController.navigate("questions/ask-question-ui")
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        QuestionPager(screenWidth, screenHeight, navController)
                    }
                }
            }
        }
    }

    @Composable
    private fun QuestionPager(screenWidth: Dp,
                              screenHeight: Dp, navController: NavController) {

        var selectedPagerIndex by rememberSaveable { mutableIntStateOf(0) }
        val state = rememberPagerState { 2 }

        LaunchedEffect(selectedPagerIndex) {
            state.animateScrollToPage(selectedPagerIndex)
        }

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
                    Text(
                        text = stringResource(R.string.label_all_question),
                        fontFamily = PBCFontFamily,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            selectedPagerIndex = 1
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.label_your_question),
                        fontFamily = PBCFontFamily,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp)
            ) {
                repeat(state.pageCount) { iteration ->
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
                .padding(top = 10.dp),
            contentPadding = PaddingValues(horizontal = 0.dp),
            snapPosition = SnapPosition.Center
        ) { page ->
            when (page) {
                0 -> AllQuestionList(screenHeight = screenHeight, navController = navController)
                1 -> YourQuestionList(screenHeight = screenHeight, navController = navController)
            }
        }
    }

    @Composable
    private fun AllQuestionList(
        screenHeight: Dp,
        navController: NavController
    ) {

    }

    @Composable
    private fun YourQuestionList(
        screenHeight: Dp,
        navController: NavController
    ) {

    }
}