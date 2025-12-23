package com.kavi.pbc.droid.ask.question.ui.manage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.ask.question.data.model.QuestionDeleteStatus
import com.kavi.pbc.droid.ask.question.data.repository.local.QuestionLocalRepository
import com.kavi.pbc.droid.ask.question.data.repository.remote.QuestionRemoteRepository
import com.kavi.pbc.droid.data.dto.pagination.PaginationRequest
import com.kavi.pbc.droid.data.dto.question.Question
import com.kavi.pbc.droid.network.model.ResultWrapper
import com.kavi.pbc.droid.network.session.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionManageViewModel @Inject constructor(
    private val remoteRepository: QuestionRemoteRepository,
    private val localQuestionRepository: QuestionLocalRepository
): ViewModel() {

    private val isInitialRequestFired = mutableStateOf(false)

    private val paginationRequest = PaginationRequest(null)
    private var isPagingReachedEnd by mutableStateOf(false)

    private val _pageIndex = MutableStateFlow(0)
    val pageIndex: StateFlow<Int> = _pageIndex

    private val _allQuestionList = MutableStateFlow<MutableList<Question>>(mutableListOf())
    val allQuestionList: StateFlow<MutableList<Question>> = _allQuestionList

    private val _userQuestionList = MutableStateFlow<MutableList<Question>>(mutableListOf())
    val userQuestionList: StateFlow<MutableList<Question>> = _userQuestionList

    private val _questionDeleteStatus = MutableStateFlow(QuestionDeleteStatus.NONE)
    val questionDeleteStatus: StateFlow<QuestionDeleteStatus> = _questionDeleteStatus

    fun refreshDataIfRequired() {
        localQuestionRepository.isQuestionModified().onSuccess { isModified ->
            if (isModified) {
                // Reset Pagination
                isPagingReachedEnd = false
                isInitialRequestFired.value = false
                paginationRequest.previousPageLastDocKey = null
                _allQuestionList.value = mutableListOf()
                // Refresh all question list
                fetchAllQuestionList()
                // Reset the question update status
                localQuestionRepository.storeQuestionModifyStatus(false)

                // Fetch User questions
                fetchUserQuestionList()
            }
        }
    }

    fun fetchAllQuestionList() {
        if (!isPagingReachedEnd) {
            if (!isInitialRequestFired.value && paginationRequest.previousPageLastDocKey == null) {
                isInitialRequestFired.value = true
                getAllQuestionList()
            } else if (isInitialRequestFired.value && paginationRequest.previousPageLastDocKey != null) {
                getAllQuestionList()
            }
        }
    }

    fun storeModifyQuestion(question: Question): String =
        localQuestionRepository.setModifyingQuestion(question = question)

    fun storeSelectedQuestion(question: Question): String =
        localQuestionRepository.setSelectedQuestion(question = question)

    fun fetchUserQuestionList() {
        Session.user?.let { user ->
            viewModelScope.launch {
                when(val response = remoteRepository.getUserQuestionList(userId = user.id!!)) {
                    is ResultWrapper.NetworkError, is ResultWrapper.HttpError, is ResultWrapper.UnAuthError -> {}
                    is ResultWrapper.Success -> {
                        response.value.body?.let {
                            _userQuestionList.value = it
                        }
                    }
                }
            }
        }
    }

    fun deleteGivenQuestion(questionId: String) {
        viewModelScope.launch {
            _questionDeleteStatus.value = QuestionDeleteStatus.PENDING
            when(remoteRepository.deleteQuestion(questionId = questionId)) {
                is ResultWrapper.NetworkError, is ResultWrapper.HttpError, is ResultWrapper.UnAuthError -> {
                    _questionDeleteStatus.value = QuestionDeleteStatus.FAILURE
                }
                is ResultWrapper.Success -> {
                    _userQuestionList.value = _userQuestionList.value
                        .filterNot { it.id == questionId }
                        .toMutableList()

                    _allQuestionList.value = _allQuestionList.value
                        .filterNot { it.id == questionId }
                        .toMutableList()

                    _questionDeleteStatus.value = QuestionDeleteStatus.SUCCESS
                }
            }
        }
    }

    private fun getAllQuestionList() {
        viewModelScope.launch {
            when (val response = remoteRepository.getAllQuestionList(paginationRequest)) {
                is ResultWrapper.NetworkError -> {}
                is ResultWrapper.HttpError -> {
                    if (response.code == 404) {
                        isPagingReachedEnd = true
                    }
                }
                is ResultWrapper.UnAuthError -> {}
                is ResultWrapper.Success -> {
                    response.value.body?.let {
                        _allQuestionList.update { currentList ->
                            (currentList + it.entityList).toMutableList()
                        }
                        paginationRequest.previousPageLastDocKey = it.previousPageLastDocKey
                    }
                }
            }
        }
    }
}