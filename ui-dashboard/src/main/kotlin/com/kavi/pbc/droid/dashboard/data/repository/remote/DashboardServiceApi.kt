package com.kavi.pbc.droid.dashboard.data.repository.remote

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.quote.Quote
import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.data.dto.news.News
import com.kavi.pbc.droid.data.dto.notification.PushTokenRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface DashboardServiceApi {

    @GET("/dashboard/get/events")
    suspend fun getDashboardEvents(): BaseResponse<List<Event>>

    @GET("/dashboard/get/daily/quotes")
    suspend fun getDashboardDailyQuotes(): BaseResponse<List<Quote>>

    @GET("/dashboard/get/news")
    suspend fun getDashboardNews(): BaseResponse<List<News>>

    @PUT("/user/update/push-token/{userId}")
    suspend fun syncPushNotificationToken(@Path("userId") userId: String,
                                          @Body pushTokenReq: PushTokenRequest): BaseResponse<String>
}