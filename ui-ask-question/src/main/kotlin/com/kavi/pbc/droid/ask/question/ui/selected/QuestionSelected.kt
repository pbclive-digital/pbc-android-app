package com.kavi.pbc.droid.ask.question.ui.selected

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kavi.pbc.droid.ask.question.R
import com.kavi.pbc.droid.ask.question.data.model.AddAnswerStatus
import com.kavi.pbc.droid.ask.question.ui.common.AnswerCommentItem
import com.kavi.pbc.droid.lib.common.ui.component.AppIconButton
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineTextField
import com.kavi.pbc.droid.lib.common.ui.component.Title
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import com.kavi.pbc.droid.lib.parent.contract.ContractServiceLocator
import com.kavi.pbc.droid.lib.parent.contract.module.AuthContract
import com.kavi.pbc.droid.network.session.Session
import javax.inject.Inject

class QuestionSelected @Inject constructor() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun QuestionSelectedUI(navController :NavController,
        selectedQuestionKey: String, viewModel: QuestionSelectedViewModel = hiltViewModel()) {

        val context = LocalContext.current
        viewModel.setSelectedQuestion(selectedQuestionKey)

        val authInviteSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
        val showAuthInviteSheet = remember { mutableStateOf(false) }

        val selectedQuestion by viewModel.selectedQuestion.collectAsState()
        val answerCommentList by viewModel.answerCommentList.collectAsState()
        val addAnswerStatus by viewModel.addAnswerStatus.collectAsState()

        val newAnswerComment = remember { mutableStateOf(TextFieldValue("")) }

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
                        modifier = Modifier
                            .padding(start = 12.dp, end = 12.dp),
                        titleText = "Q: ${selectedQuestion.title}",
                    )

                    Text(
                        text = selectedQuestion.content,
                        fontFamily = PBCFontFamily,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .padding(top = 16.dp, start = 12.dp, end = 12.dp)
                            .fillMaxWidth()
                    )

                    Row (
                        modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "by ${selectedQuestion.author.firstName} ${selectedQuestion.author.lastName}",
                            fontFamily = PBCFontFamily,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Justify,
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .border(
                                    border = BorderStroke(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.tertiary
                                    ),
                                    shape = CircleShape
                                )
                        ) {
                            AsyncImage(
                                model = selectedQuestion.author.profilePicUrl,
                                contentDescription = "Profile Picture",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(5.dp)
                                    .clip(CircleShape)
                            )
                        }
                    }

                    Text(
                        text = stringResource(R.string.label_question_answers),
                        fontFamily = PBCFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .padding(top = 16.dp, start = 8.dp)
                            .fillMaxWidth()
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 30.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(answerCommentList) { answerComment ->
                            AnswerCommentItem(answerComment = answerComment)
                        }
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AppOutlineTextField (
                                    modifier = Modifier
                                        .weight(1f),
                                    headingText = stringResource(R.string.label_question_your_answer),
                                    contentText = newAnswerComment,
                                    onValueChange = { newValue ->
                                        newAnswerComment.value = newValue
                                    }
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                AppIconButton(
                                    modifier = Modifier.padding(top = 8.dp),
                                    icon = painterResource(R.drawable.icon_send),
                                    buttonSize = 50.dp
                                ) {
                                    if(Session.isLogIn()) {
                                        viewModel.addAnswerCommentToQuestion(newAnswerComment.value.text)
                                    } else {
                                        showAuthInviteSheet.value = true
                                    }
                                }
                            }
                        }
                    }
                }
            }

            when (addAnswerStatus) {
                AddAnswerStatus.NONE -> {}
                AddAnswerStatus.PENDING -> {

                }
                AddAnswerStatus.FAILURE -> {
                    Toast.makeText(context, stringResource(R.string.label_question_add_answer_failure), Toast.LENGTH_LONG).show()
                }
                AddAnswerStatus.SUCCESS -> {
                    newAnswerComment.value = TextFieldValue()
                    Toast.makeText(context, stringResource(R.string.label_question_add_answer_success), Toast.LENGTH_LONG).show()
                }
            }
        }

        if (showAuthInviteSheet.value) {
            ContractServiceLocator.locate(AuthContract::class).AuthInviteBottomSheet(
                sheetState = authInviteSheetState, showSheet = showAuthInviteSheet
            ) {
                showAuthInviteSheet.value = false
                navController.navigate("dashboard/to/auth")
            }
        }
    }
}