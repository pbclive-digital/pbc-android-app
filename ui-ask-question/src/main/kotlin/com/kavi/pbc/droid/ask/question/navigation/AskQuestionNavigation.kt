package com.kavi.pbc.droid.ask.question.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kavi.pbc.droid.ask.question.ui.ask.AskOrModifyQuestion
import com.kavi.pbc.droid.ask.question.ui.manage.QuestionManage
import com.kavi.pbc.droid.ask.question.ui.selected.QuestionSelected
import javax.inject.Inject

class AskQuestionNavigation @Inject constructor() {

    @Inject
    lateinit var questionManage: QuestionManage

    @Inject
    lateinit var askOrModifyQuestion: AskOrModifyQuestion

    @Inject
    lateinit var selectedQuestion: QuestionSelected

    @Composable
    fun AskQuestionNavGraph() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "questions/questions-ui") {
            composable (route = "questions/questions-ui") {
                questionManage.QuestionManageUI(navController = navController)
            }
            composable (route = "questions/ask-question-ui") {
                askOrModifyQuestion.AskOrModifyQuestionUI(navController = navController)
            }
            composable (route = "questions/modify-question-ui/{modifyQuestionKey}") { backStackEntry ->
                val modifyQuestionKey = backStackEntry.arguments?.getString("modifyQuestionKey")
                askOrModifyQuestion.AskOrModifyQuestionUI(navController = navController, modifyingQuestionKey = modifyQuestionKey)
            }
            composable (route = "questions/selected-questions/{selectedQuestionKey}") { backStackEntry ->
                val selectedQuestionKey = backStackEntry.arguments?.getString("selectedQuestionKey")
                selectedQuestionKey?.let {
                    selectedQuestion.QuestionSelectedUI(navController = navController, selectedQuestionKey = it)
                }
            }
        }
    }
}