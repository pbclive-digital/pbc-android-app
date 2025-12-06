package com.kavi.pbc.droid.news.data.repository.local

import com.kavi.pbc.droid.data.dto.news.News
import com.kavi.pbc.droid.lib.datastore.AppInMemoryStore
import java.util.UUID
import javax.inject.Inject

class NewsLocalRepository @Inject constructor(
    val inMemoryStore: AppInMemoryStore
) {
    fun setModifyingNews(news: News): String {
        val randomNewsKey = UUID.randomUUID().toString()
        inMemoryStore.storeValue(randomNewsKey, news)
        return randomNewsKey
    }

    fun getModifyingNews(tempNewsKey: String): Result<News> {
        val news = inMemoryStore.retrieveValue<News>(key = tempNewsKey)
        return news
    }
}