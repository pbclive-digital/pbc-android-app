package com.kavi.pbc.droid.ask.question.ui.manage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.ask.question.data.repository.remote.QuestionRemoteRepository
import com.kavi.pbc.droid.data.dto.pagination.PaginationRequest
import com.kavi.pbc.droid.data.dto.question.Question
import com.kavi.pbc.droid.network.model.ResultWrapper
import com.kavi.pbc.droid.network.session.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionManageViewModel @Inject constructor(
    private val remoteRepository: QuestionRemoteRepository
): ViewModel() {

    private val paginationRequest = mutableStateOf(PaginationRequest(null))
    private val _allQuestionList = MutableStateFlow<MutableList<Question>>(mutableListOf())
    val allQuestionList: StateFlow<MutableList<Question>> = _allQuestionList

    private val _userQuestionList = MutableStateFlow<MutableList<Question>>(mutableListOf())
    val userQuestionList: StateFlow<MutableList<Question>> = _userQuestionList

    fun fetchAllQuestionList() {
        viewModelScope.launch {
            when(val response = remoteRepository.getAllQuestionList(paginationRequest.value)) {
                is ResultWrapper.NetworkError, is ResultWrapper.HttpError, is ResultWrapper.UnAuthError -> {}
                is ResultWrapper.Success -> {
                    response.value.body?.let {
                        _allQuestionList.value = it.entityList
                        paginationRequest.value = PaginationRequest(it.previousPageLastDocKey)
                    }
                }
            }
        }
    }

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
}