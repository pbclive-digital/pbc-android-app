package com.kavi.pbc.droid.ask.question

import androidx.compose.runtime.Composable
import com.kavi.pbc.droid.ask.question.navigation.AskQuestionNavigation
import com.kavi.pbc.droid.lib.parent.contract.module.AskQuestionContract
import javax.inject.Inject

class AskQuestionContractImpl @Inject constructor(): AskQuestionContract {

    @Inject
    lateinit var navigation: AskQuestionNavigation
    
    @Composable
    override fun RetrieveNavGraph() {
        navigation.AskQuestionNavGraph()
    }
}