package com.kavi.pbc.droid.ask.question.ui.ask

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kavi.pbc.droid.ask.question.R
import com.kavi.pbc.droid.ask.question.data.model.QuestionModifyOrCreateStatus
import com.kavi.pbc.droid.lib.common.ui.component.AppFilledButton
import com.kavi.pbc.droid.lib.common.ui.component.AppFullScreenLoader
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineMultiLineTextField
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineTextField
import com.kavi.pbc.droid.lib.common.ui.component.Title
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import javax.inject.Inject

class AskOrModifyQuestion @Inject constructor() {

    @Composable
    fun AskOrModifyQuestionUI(navController: NavController,
                              modifyingQuestionKey: String? = null,
                              viewModel: AskOrModifyViewModel = hiltViewModel()) {

        val context = LocalContext.current
        var isModify by remember { mutableStateOf(false) }

        modifyingQuestionKey?.let {
            isModify = true
            viewModel.setModifyingQuestion(questionKey = it)
        }

        val askQuestionTitle = remember { mutableStateOf(TextFieldValue(viewModel.askOrModifyQuestion.value.title)) }
        val askQuestionContent = remember { mutableStateOf(TextFieldValue(viewModel.askOrModifyQuestion.value.content)) }
        var isPrivateQuestion by remember { mutableStateOf(false) }

        val questionCreateOrModifyStatus by viewModel.questionCreateOrModifyStatus.collectAsState()

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
                        titleText = stringResource(R.string.label_question_new),
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 30.dp)
                            .verticalScroll(state = rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AppOutlineTextField (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            headingText = stringResource(R.string.label_question_title),
                            contentText = askQuestionTitle,
                            onValueChange = { newValue ->
                                askQuestionTitle.value = newValue
                                viewModel.updateQuestionTitle(askQuestionTitle.value.text)
                            }
                        )

                        AppOutlineMultiLineTextField (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp)
                                .height(250.dp),
                            headingText = stringResource(R.string.label_question_content),
                            contentText = askQuestionContent,
                            maxLines = 20,
                            onValueChange = { newValue ->
                                askQuestionContent.value = newValue
                                viewModel.updateQuestionContent(askQuestionContent.value.text)
                            }
                        )

                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, start = 4.dp, end = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(end = 12.dp),
                                text = stringResource(R.string.label_question_create_privacy),
                                fontFamily = PBCFontFamily,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onSurface,
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Switch(
                                checked = isPrivateQuestion, // The current state of the switch
                                onCheckedChange = { newState ->
                                    isPrivateQuestion = newState // Update the state when the user interacts with the switch
                                    viewModel.updatePrivacyStatus(isPrivateQuestion)
                                }
                            )
                        }

                        Text(
                            text = stringResource(R.string.phrase_question_create_privacy),
                            fontFamily = PBCFontFamily,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Justify,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        AppFilledButton(
                            label = if (isModify) stringResource(R.string.label_question_modify)
                            else stringResource(R.string.label_question_create),
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            viewModel.createOrModifyQuestion(isModify = isModify)
                        }
                    }
                }
            }

            when(questionCreateOrModifyStatus) {
                QuestionModifyOrCreateStatus.NONE -> {}
                QuestionModifyOrCreateStatus.PENDING -> {
                    AppFullScreenLoader()
                }
                QuestionModifyOrCreateStatus.FAILURE -> {
                    Toast.makeText(context, stringResource(R.string.label_question_create_failed), Toast.LENGTH_LONG).show()
                }
                QuestionModifyOrCreateStatus.SUCCESS -> {
                    val toastText = if (isModify) {
                        stringResource(R.string.label_question_modify_success)
                    } else {
                        stringResource(R.string.label_question_create_success)
                    }
                    Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
                    navController.popBackStack()
                }
            }
        }
    }
}