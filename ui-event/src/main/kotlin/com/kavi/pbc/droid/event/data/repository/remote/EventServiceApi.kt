package com.kavi.pbc.droid.event.data.repository.remote

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.data.dto.event.potluck.EventPotluck
import com.kavi.pbc.droid.data.dto.event.potluck.EventPotluckContributor
import com.kavi.pbc.droid.data.dto.event.register.EventRegistration
import com.kavi.pbc.droid.data.dto.event.register.EventRegistrationItem
import com.kavi.pbc.droid.data.dto.event.signup.EventSignUpSheet
import com.kavi.pbc.droid.data.dto.event.signup.SheetContributor
import com.kavi.pbc.droid.data.dto.event.signup.SignUpSheetItem
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

    @GET("/event/get/registration/{eventId}")
    suspend fun getEventRegistration(@Path("eventId") eventId: String): BaseResponse<EventRegistration>

    @POST("/event/register/{eventId}")
    suspend fun registerToEvent(@Path("eventId") eventId: String, @Body eventRegistrationItem: EventRegistrationItem): BaseResponse<EventRegistration>

    @DELETE("/event/unregister/{eventId}/{userId}")
    suspend fun unregisterFromEvent(@Path("eventId") eventId: String, @Path("userId") userId: String): BaseResponse<EventRegistration>

    @GET("/event/get/potluck/{eventId}")
    suspend fun getEventPotluck(@Path("eventId") eventId: String): BaseResponse<EventPotluck>

    @POST("/event/potluck/sign-up/{eventId}/{potluckItemId}")
    suspend fun signUpToPotluck(@Path("eventId") eventId: String, @Path("potluckItemId") potluckItemId: String,
                                @Body potluckContributor: EventPotluckContributor): BaseResponse<EventPotluck>

    @DELETE("/event/potluck/sign-out/{eventId}/{potluckItemId}/{contributorId}")
    suspend fun signOutFromPotluck(@Path("eventId") eventId: String, @Path("potluckItemId") potluckItemId: String,
                                   @Path("contributorId") contributorId: String): BaseResponse<EventPotluck>

    @GET("/event/get/sign-up-sheet/{eventId}")
    suspend fun getSignUpSheetList(@Path("eventId") eventId: String): BaseResponse<EventSignUpSheet>

    @GET("/event/get/sign-up-sheet/{eventId}/{sheetId}")
    suspend fun getSignUpSheet(@Path("eventId") eventId: String,
                                     @Path("sheetId") sheetId: String): BaseResponse<SignUpSheetItem>

    @POST("/event/sign-up-sheet/sign-up/{eventId}/{sheetId}")
    suspend fun signUpToSelectedSignUpSheet(@Path("eventId") eventId: String,
                                            @Path("sheetId") sheetId: String,
                                @Body sheetContributor: SheetContributor): BaseResponse<EventSignUpSheet>

    @DELETE("/event/sign-up-sheet/sign-out/{eventId}/{sheetId}/{contributorId}")
    suspend fun signOutFromSelectedSignUpSheet(@Path("eventId") eventId: String, @Path("sheetId") sheetId: String,
                                   @Path("contributorId") contributorId: String): BaseResponse<EventSignUpSheet>
}