package com.kavi.pbc.droid.appointment.data.repository

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.appointment.Appointment
import com.kavi.pbc.droid.data.dto.appointment.AppointmentRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AppointmentServiceApi {

    @POST("/event/create")
    suspend fun createAppointment(@Body appointmentReq: AppointmentRequest): BaseResponse<String>

    @GET("/appointment/get/{userId}")
    suspend fun getUserAppointmentList(@Path("userId") userId: String): BaseResponse<List<Appointment>>
}