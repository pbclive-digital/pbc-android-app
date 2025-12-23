package com.kavi.pbc.droid.ask.question.ui.selected

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.ask.question.data.model.AddAnswerStatus
import com.kavi.pbc.droid.ask.question.data.repository.local.QuestionLocalRepository
import com.kavi.pbc.droid.ask.question.data.repository.remote.QuestionRemoteRepository
import com.kavi.pbc.droid.data.dto.question.AnswerComment
import com.kavi.pbc.droid.data.dto.question.Question
import com.kavi.pbc.droid.data.dto.user.UserSummary
import com.kavi.pbc.droid.network.model.ResultWrapper
import com.kavi.pbc.droid.network.session.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionSelectedViewModel @Inject constructor(
    private val localQuestionRepository: QuestionLocalRepository,
    private val remoteRepository: QuestionRemoteRepository
): ViewModel() {

    private val _selectedQuestion = MutableStateFlow(Question())
    val selectedQuestion: StateFlow<Question> = _selectedQuestion

    private val _answerCommentList = MutableStateFlow<MutableList<AnswerComment>>(mutableListOf())
    val answerCommentList: StateFlow<MutableList<AnswerComment>> = _answerCommentList

    private val _addAnswerStatus = MutableStateFlow(AddAnswerStatus.NONE)
    val addAnswerStatus: StateFlow<AddAnswerStatus> = _addAnswerStatus

    fun setSelectedQuestion(questionKey: String) {
        if (_selectedQuestion.value.id == null) {
            localQuestionRepository.getSelectedQuestion(tempQuestionKey = questionKey).onSuccess { question ->
                _selectedQuestion.value = question
                _answerCommentList.value = question.answerList
            }
        }
    }

    fun addAnswerCommentToQuestion(givenAnswerComment: String) {
        Session.user?.let { user ->
            val answerComment = AnswerComment(
                comment = givenAnswerComment,
                createdTime = System.currentTimeMillis(),
                author = UserSummary(
                    id = user.id!!,
                    name = "${user.firstName} ${user.lastName}",
                    imageUrl = user.profilePicUrl
                )
            )

            viewModelScope.launch {
                _addAnswerStatus.value = AddAnswerStatus.PENDING
                when(val response = remoteRepository.createNewAnswer(_selectedQuestion.value.id!!, answerComment)) {
                    is ResultWrapper.NetworkError, is ResultWrapper.HttpError, is ResultWrapper.UnAuthError -> {
                        _addAnswerStatus.value = AddAnswerStatus.FAILURE
                    }
                    is ResultWrapper.Success -> {
                        _addAnswerStatus.value = AddAnswerStatus.SUCCESS
                        response.value.body?.let { updatedQuestion ->

                            // Store modified question in in-memory store to update previous screen data
                            localQuestionRepository.storeQuestionModifyStatus(true)

                            _answerCommentList.update { currentList ->
                                ((currentList + answerComment) as MutableList<AnswerComment>)
                            }
                        }
                    }
                }
            }
        }
    }
}