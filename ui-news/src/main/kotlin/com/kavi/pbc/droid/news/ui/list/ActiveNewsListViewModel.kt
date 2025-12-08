package com.kavi.pbc.droid.news.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.data.dto.news.News
import com.kavi.pbc.droid.network.model.ResultWrapper
import com.kavi.pbc.droid.news.data.model.NewsListFetchStatus
import com.kavi.pbc.droid.news.data.repository.remote.NewsRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActiveNewsListViewModel @Inject constructor(
    val remoteRepository: NewsRemoteRepository
): ViewModel() {

    private val _activeNewsList = MutableStateFlow<MutableList<News>>(mutableListOf())
    val activeNewsList: StateFlow<MutableList<News>> = _activeNewsList

    private val _activeNewsFetchStatus = MutableStateFlow(NewsListFetchStatus.NONE)
    val activeNewsFetchStatus: StateFlow<NewsListFetchStatus> = _activeNewsFetchStatus

    fun fetchActiveNewsList() {
        _activeNewsFetchStatus.value = NewsListFetchStatus.PENDING
        viewModelScope.launch {
            when(val response = remoteRepository.getActiveNews()) {
                is ResultWrapper.NetworkError, is ResultWrapper.UnAuthError, is ResultWrapper.HttpError -> {
                    _activeNewsFetchStatus.value = NewsListFetchStatus.FAILURE
                }
                is ResultWrapper.Success -> {
                    response.value.body?.let {
                        if (it.isNotEmpty()) {
                            _activeNewsFetchStatus.value = NewsListFetchStatus.SUCCESS
                            _activeNewsList.value = it
                        } else {
                            _activeNewsFetchStatus.value = NewsListFetchStatus.EMPTY
                        }
                    }
                }
            }
        }
    }
}