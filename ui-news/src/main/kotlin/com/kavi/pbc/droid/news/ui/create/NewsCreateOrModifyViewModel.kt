package com.kavi.pbc.droid.news.ui.create

import android.net.Uri
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
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
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

    private var _newsImageUri = MutableStateFlow<Uri?>(null)
    val newsImageUri: StateFlow<Uri?> = _newsImageUri

    private var newsImageFile: File? = null

    fun setModifyingNews(newsKey: String) {
        localNewsRepository.getModifyingNews(tempNewsKey = newsKey).onSuccess { news ->
            _news.value = news
            _newsImageUri.value = Uri.parse(news.newsImage)
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

    fun updateNewsImageUrl(newsImage: Uri) {
        _newsImageUri.value = newsImage
    }

    fun updateNewsImageFile(newsImage: File) {
        newsImageFile = newsImage
    }

    fun uploadNewsImageAndCreateOrModifyNews(isModify: Boolean = false) {
        val imagePartRequest = createMultiPartRequest(newsImageFile)
        val formatedNewsTitle = _news.value.title.replace(" ", "_").replace("-", "_")

        if (imagePartRequest != null) {
            _newsCreationStatus.value = NewsCreationStatus.PENDING
            viewModelScope.launch {
                when(val response = remoteNewsRemoteRepository.uploadNewsImage(formatedNewsTitle, imagePartRequest)) {
                    is ResultWrapper.NetworkError -> {}
                    is ResultWrapper.HttpError -> {
                        if (isModify) {
                            updateNews()
                        } else {
                            createNews()
                        }
                    }
                    is ResultWrapper.UnAuthError -> {}
                    is ResultWrapper.Success -> {
                        response.value.body?.let {
                            _news.value.newsImage = it
                            if (isModify) {
                                updateNews()
                            } else {
                                createNews()
                            }
                        }
                    }
                }
            }
        } else {
            _newsCreationStatus.value = NewsCreationStatus.PENDING
            if (isModify) {
                updateNews()
            } else {
                createNews()
            }
        }
    }

    private fun createMultiPartRequest(providedFile: File?): MultipartBody.Part? {
        providedFile?.let { file ->
            val requestFile = file.asRequestBody("image/png".toMediaType())
            val imagePart = MultipartBody.Part.createFormData("newsImage", file.name, requestFile)
            return imagePart
        }?: run {
            return null
        }
    }

    private fun createNews() {
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

    private fun updateNews() {
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