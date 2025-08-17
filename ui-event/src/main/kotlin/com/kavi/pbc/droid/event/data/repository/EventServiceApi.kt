package com.kavi.pbc.droid.event.data.repository

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.event.Event
import retrofit2.http.GET

interface EventServiceApi {

    @GET("/event/get/upcoming")
    suspend fun getUpcomingEvents(): BaseResponse<List<Event>>

    @GET("/event/get/past")
    suspend fun getPastEvents(): BaseResponse<List<Event>>
}