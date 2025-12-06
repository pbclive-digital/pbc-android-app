package com.kavi.pbc.droid.news.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.data.dto.news.News
import com.kavi.pbc.droid.network.model.ResultWrapper
import com.kavi.pbc.droid.news.data.model.NewsCreationStatus
import com.kavi.pbc.droid.news.data.repository.local.NewsLocalRepository
import com.kavi.pbc.droid.news.data.repository.remote.NewsRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsCreateOrModifyViewModel @Inject constructor(
    val localNewsRepository: NewsLocalRepository,
    val remoteNewsRemoteRepository: NewsRemoteRepository
): ViewModel() {

    private val _newsCreationStatus = MutableStateFlow(NewsCreationStatus.NONE)
    val newsCreationStatus: StateFlow<NewsCreationStatus> = _newsCreationStatus

    private val _news = MutableStateFlow(News(createdTime = System.currentTimeMillis()))
    val news: StateFlow<News> = _news

    fun setModifyingNews(newsKey: String) {
        localNewsRepository.getModifyingNews(tempNewsKey = newsKey).onSuccess { news ->
            _news.value = news
        }
    }

    fun updateNewsHeadline(headline: String) {
        _news.value.title = headline
    }

    fun updateNewsContent(content: String) {
        _news.value.content = content
    }

    fun updateNewsLink(newsLink: String) {
        _news.value.facebookLink = newsLink
    }

    fun createNews() {
        _newsCreationStatus.value = NewsCreationStatus.PENDING
        viewModelScope.launch {
            when(val response = remoteNewsRemoteRepository.createNews(news = _news.value)) {
                is ResultWrapper.NetworkError, is ResultWrapper.HttpError, is ResultWrapper.UnAuthError -> {
                    _newsCreationStatus.value = NewsCreationStatus.FAILURE
                }
                is ResultWrapper.Success -> {
                    response.value.body?.let {
                        _newsCreationStatus.value = NewsCreationStatus.SUCCESS
                    }
                }
            }
        }
    }

    fun updateNews() {
        _newsCreationStatus.value = NewsCreationStatus.PENDING
        viewModelScope.launch {
            when(val response = remoteNewsRemoteRepository.updateNews(newsId = _news.value.id!!, news = _news.value)) {
                is ResultWrapper.NetworkError, is ResultWrapper.HttpError, is ResultWrapper.UnAuthError -> {
                    _newsCreationStatus.value = NewsCreationStatus.FAILURE
                }
                is ResultWrapper.Success -> {
                    response.value.body?.let {
                        _newsCreationStatus.value = NewsCreationStatus.SUCCESS
                    }
                }
            }
        }
    }
}