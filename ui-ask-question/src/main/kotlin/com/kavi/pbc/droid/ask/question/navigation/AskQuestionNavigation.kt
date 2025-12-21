package com.kavi.pbc.droid.ask.question.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kavi.pbc.droid.ask.question.ui.ask.AskQuestion
import com.kavi.pbc.droid.ask.question.ui.manage.QuestionManage
import javax.inject.Inject

class AskQuestionNavigation @Inject constructor() {

    @Inject
    lateinit var questionManage: QuestionManage

    @Inject
    lateinit var askQuestion: AskQuestion

    @Composable
    fun AskQuestionNavGraph() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "questions/questions-ui") {
            composable (route = "questions/questions-ui") {
                questionManage.QuestionManageUI(navController = navController)
            }
            composable (route = "questions/ask-question-ui") {
                askQuestion.AskQuestionUI(navController = navController)
            }
        }
    }
}