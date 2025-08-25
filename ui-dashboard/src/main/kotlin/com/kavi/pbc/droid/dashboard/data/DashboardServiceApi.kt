package com.kavi.pbc.droid.dashboard.data

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.event.Event
import retrofit2.http.GET

interface DashboardServiceApi {

    @GET("/dashboard/get/events")
    suspend fun getDashboardEvents(): BaseResponse<List<Event>>
}