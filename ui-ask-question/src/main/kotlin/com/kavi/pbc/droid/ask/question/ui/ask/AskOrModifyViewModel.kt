package com.kavi.pbc.droid.ask.question.ui.ask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.ask.question.data.model.QuestionModifyOrCreateStatus
import com.kavi.pbc.droid.ask.question.data.repository.local.QuestionLocalRepository
import com.kavi.pbc.droid.ask.question.data.repository.remote.QuestionRemoteRepository
import com.kavi.pbc.droid.data.dto.question.Question
import com.kavi.pbc.droid.network.model.ResultWrapper
import com.kavi.pbc.droid.network.session.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AskOrModifyViewModel @Inject constructor(
    private val remoteQuestionRepository: QuestionRemoteRepository,
    private val localQuestionRepository: QuestionLocalRepository
): ViewModel() {

    private val _askOrModifyQuestion = MutableStateFlow(Question())
    val askOrModifyQuestion: StateFlow<Question> = _askOrModifyQuestion

    private val _questionCreateOrModifyStatus = MutableStateFlow(QuestionModifyOrCreateStatus.NONE)
    val questionCreateOrModifyStatus: StateFlow<QuestionModifyOrCreateStatus> = _questionCreateOrModifyStatus

    init {
        Session.user?.let {
            _askOrModifyQuestion.value = Question(
                authorId = it.id!!,
                author = it,
            )
        }
    }

    fun setModifyingQuestion(questionKey: String) {
        localQuestionRepository.getModifyingQuestion(tempQuestionKey = questionKey).onSuccess { question ->
            _askOrModifyQuestion.value = question
        }
    }

    fun updateQuestionTitle(title: String) {
        _askOrModifyQuestion.value.title = title
    }

    fun updateQuestionContent(content: String) {
        _askOrModifyQuestion.value.content = content
    }

    fun createOrModifyQuestion(isModify: Boolean) {
        if (!isModify) {
            viewModelScope.launch {
                _questionCreateOrModifyStatus.value = QuestionModifyOrCreateStatus.PENDING
                when(remoteQuestionRepository.createNewQuestion(_askOrModifyQuestion.value)) {
                    is ResultWrapper.NetworkError, is ResultWrapper.HttpError, is ResultWrapper.UnAuthError -> {
                        _questionCreateOrModifyStatus.value = QuestionModifyOrCreateStatus.FAILURE
                    }
                    is ResultWrapper.Success -> {
                        _questionCreateOrModifyStatus.value = QuestionModifyOrCreateStatus.SUCCESS
                    }
                }
            }
        } else {
            viewModelScope.launch {
                _questionCreateOrModifyStatus.value = QuestionModifyOrCreateStatus.PENDING
                when(remoteQuestionRepository.modifyQuestion(_askOrModifyQuestion.value.id!!, _askOrModifyQuestion.value)) {
                    is ResultWrapper.NetworkError, is ResultWrapper.HttpError, is ResultWrapper.UnAuthError -> {
                        _questionCreateOrModifyStatus.value = QuestionModifyOrCreateStatus.FAILURE
                    }
                    is ResultWrapper.Success -> {
                        _questionCreateOrModifyStatus.value = QuestionModifyOrCreateStatus.SUCCESS
                    }
                }
            }
        }
    }
}