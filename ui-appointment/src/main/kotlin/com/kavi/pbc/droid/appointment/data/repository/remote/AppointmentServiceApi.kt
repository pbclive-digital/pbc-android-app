package com.kavi.pbc.droid.appointment.data.repository.remote

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.appointment.Appointment
import com.kavi.pbc.droid.data.dto.appointment.AppointmentRequest
import com.kavi.pbc.droid.data.dto.appointment.AppointmentRequestEligibility
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AppointmentServiceApi {

    @POST("/appointment/create")
    suspend fun createAppointment(@Body appointment: Appointment): BaseResponse<String>

    @POST("/appointment/request/create")
    suspend fun createAppointmentRequest(@Body appointmentReq: AppointmentRequest): BaseResponse<String>

    @GET("/appointment/request/create/eligibility/{userId}")
    suspend fun validateRequestCreationEligibility(@Path("userId") userId: String): BaseResponse<AppointmentRequestEligibility>

    @GET("/appointment/get/{userId}")
    suspend fun getUserAppointmentList(@Path("userId") userId: String): BaseResponse<List<Appointment>>

    @GET("/appointment/request/get/{userId}")
    suspend fun getUserAppointmentRequestList(@Path("userId") userId: String): BaseResponse<List<AppointmentRequest>>

    @DELETE("/appointment/delete/{appointmentId}")
    suspend fun deleteAppointment(@Path("appointmentId") appointmentId: String): BaseResponse<String>

    @DELETE("/appointment/request/delete/{appointmentReqId}")
    suspend fun deleteAppointmentRequest(@Path("appointmentReqId") appointmentReqId: String): BaseResponse<String>
}