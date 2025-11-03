package com.kavi.pbc.droid.event.data.repository.remote

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.event.Event
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface EventServiceApi {

    @GET("/event/get/upcoming")
    suspend fun getUpcomingEvents(): BaseResponse<MutableList<Event>>

    @GET("/event/get/past")
    suspend fun getPastEvents(): BaseResponse<MutableList<Event>>

    @GET("/event/get/draft")
    suspend fun getDraftEvents(): BaseResponse<MutableList<Event>>

    @Multipart
    @POST("/event/add/image/{eventName}")
    suspend fun uploadEventImage(@Path("eventName") eventName: String, @Part file: MultipartBody.Part): BaseResponse<String>

    @POST("/event/create")
    suspend fun createNewEvent(@Body event: Event): BaseResponse<String>

    @PUT("/event/update/{eventId}")
    suspend fun updateEvent(@Path("eventId") eventId: String, @Body event: Event): BaseResponse<Event>

    @PUT("/event/put/publish/{eventId}")
    suspend fun publishDraftEvent(@Path("eventId") eventId: String, @Body event: Event): BaseResponse<Event>

    @DELETE("/event/delete/{eventId}")
    suspend fun deleteEvent(@Path("eventId") eventId: String): BaseResponse<String>
}