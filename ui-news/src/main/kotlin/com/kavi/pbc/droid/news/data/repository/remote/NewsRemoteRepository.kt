package com.kavi.pbc.droid.news.data.repository.remote

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.news.News
import com.kavi.pbc.droid.network.Network
import com.kavi.pbc.droid.network.model.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class NewsRemoteRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val network: Network
) {

    private val newsServiceApi = network.getRetrofit().create(NewsServiceApi::class.java)

    suspend fun getDraftNews(): ResultWrapper<BaseResponse<MutableList<News>>> {
        return network.invokeApiCall(dispatcher) { newsServiceApi.fetchDraftNewsList() }
    }

    suspend fun getActiveNews(): ResultWrapper<BaseResponse<MutableList<News>>> {
        return network.invokeApiCall(dispatcher) { newsServiceApi.fetchActiveNewsList() }
    }

    suspend fun createNews(news: News): ResultWrapper<BaseResponse<String>> {
        return network.invokeApiCall(dispatcher) { newsServiceApi.createNews(news) }
    }

    suspend fun updateNews(newsId: String, news: News): ResultWrapper<BaseResponse<News>> {
        return network.invokeApiCall(dispatcher) { newsServiceApi.updateNews(newsId, news) }
    }

    suspend fun deleteNews(newsId: String): ResultWrapper<BaseResponse<String>> {
        return network.invokeApiCall(dispatcher) { newsServiceApi.deleteNews(newsId) }
    }
}