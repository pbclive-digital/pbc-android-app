package com.kavi.pbc.droid.appointment.data.repository.remote

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.appointment.Appointment
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AppointmentServiceApi {

    @POST("/appointment/create")
    suspend fun createAppointment(@Body appointment: Appointment): BaseResponse<String>

    @GET("/appointment/get/{userId}")
    suspend fun getUserAppointmentList(@Path("userId") userId: String): BaseResponse<List<Appointment>>

    @DELETE("/appointment/delete/{appointmentId}")
    suspend fun deleteAppointment(@Path("appointmentId") appointmentId: String): BaseResponse<String>
}