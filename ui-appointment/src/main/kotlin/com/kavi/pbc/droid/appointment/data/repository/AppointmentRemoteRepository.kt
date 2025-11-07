package com.kavi.pbc.droid.appointment.data.repository

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.appointment.Appointment
import com.kavi.pbc.droid.data.dto.appointment.AppointmentRequest
import com.kavi.pbc.droid.network.Network
import com.kavi.pbc.droid.network.model.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AppointmentRemoteRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val network: Network
) {

    private val appointmentServiceApi = network.getRetrofit()
        .create(AppointmentServiceApi::class.java)

    suspend fun createAppointment(appointmentReq: AppointmentRequest): ResultWrapper<BaseResponse<String>> {
        return network.invokeApiCall(dispatcher) { appointmentServiceApi.createAppointment(appointmentReq) }
    }

    suspend fun getUserAppointmentList(userId: String): ResultWrapper<BaseResponse<List<Appointment>>> {
        return network.invokeApiCall(dispatcher) { appointmentServiceApi.getUserAppointmentList(userId = userId) }
    }
}