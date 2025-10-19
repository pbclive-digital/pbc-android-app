package com.kavi.pbc.droid.event.data.repository.remote

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.event.Event
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface EventServiceApi {

    @GET("/event/get/upcoming")
    suspend fun getUpcomingEvents(): BaseResponse<MutableList<Event>>

    @GET("/event/get/past")
    suspend fun getPastEvents(): BaseResponse<MutableList<Event>>

    @GET("/event/get/draft")
    suspend fun getDraftEvents(): BaseResponse<MutableList<Event>>

    @PUT("/event/put/publish/{eventId}")
    suspend fun publishDraftEvent(@Path("eventId") eventId: String, @Body event: Event): BaseResponse<Event>

    @DELETE("/event/delete/{eventId}")
    suspend fun deleteEvent(@Path("eventId") eventId: String): BaseResponse<String>
}