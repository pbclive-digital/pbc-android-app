package com.kavi.pbc.droid.ask.question.ui.selected

import androidx.lifecycle.ViewModel
import com.kavi.pbc.droid.ask.question.data.repository.local.QuestionLocalRepository
import com.kavi.pbc.droid.data.dto.question.Question
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class QuestionSelectedViewModel @Inject constructor(
    private val localQuestionRepository: QuestionLocalRepository
): ViewModel() {

    private val _selectedQuestion = MutableStateFlow(Question())
    val selectedQuestion: StateFlow<Question> = _selectedQuestion

    fun setSelectedQuestion(questionKey: String) {
        localQuestionRepository.getSelectedQuestion(tempQuestionKey = questionKey).onSuccess { question ->
            _selectedQuestion.value = question
        }
    }
}