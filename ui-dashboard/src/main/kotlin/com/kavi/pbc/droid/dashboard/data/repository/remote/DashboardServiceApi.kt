package com.kavi.pbc.droid.dashboard.data.repository.remote

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.quote.Quote
import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.data.dto.news.News
import retrofit2.http.GET

interface DashboardServiceApi {

    @GET("/dashboard/get/events")
    suspend fun getDashboardEvents(): BaseResponse<List<Event>>

    @GET("/dashboard/get/daily/quotes")
    suspend fun getDashboardDailyQuotes(): BaseResponse<List<Quote>>

    @GET("/dashboard/get/news")
    suspend fun getDashboardNews(): BaseResponse<List<News>>
}