package com.kavi.pbc.droid.news.ui.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.data.dto.news.News
import com.kavi.pbc.droid.network.model.ResultWrapper
import com.kavi.pbc.droid.news.data.repository.remote.NewsRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsManageViewModel @Inject constructor(
    val newsRemoteRepository: NewsRemoteRepository
): ViewModel() {

    private val _draftNewsList = MutableStateFlow<MutableList<News>>(mutableListOf())
    val draftNewsList: StateFlow<MutableList<News>> = _draftNewsList

    private val _activeNewsList = MutableStateFlow<MutableList<News>>(mutableListOf())
    val activeNewsList: StateFlow<MutableList<News>> = _activeNewsList

    fun fetchDraftNewsList() {
        viewModelScope.launch {
            when(val response = newsRemoteRepository.getDraftNews()) {
                is ResultWrapper.NetworkError -> {}
                is ResultWrapper.HttpError -> {}
                is ResultWrapper.UnAuthError -> {}
                is ResultWrapper.Success -> {
                    response.value.body?.let {
                        _draftNewsList.value = it
                    }
                }
            }
        }
    }

    fun fetchActiveNewsList() {
        viewModelScope.launch {
            when(val response = newsRemoteRepository.getActiveNews()) {
                is ResultWrapper.NetworkError -> {}
                is ResultWrapper.HttpError -> {}
                is ResultWrapper.UnAuthError -> {}
                is ResultWrapper.Success -> {
                    response.value.body?.let {
                        _activeNewsList.value = it
                    }
                }
            }
        }
    }
}