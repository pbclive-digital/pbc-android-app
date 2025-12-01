package com.kavi.pbc.droid.appointment.data.repository.remote

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.appointment.Appointment
import com.kavi.pbc.droid.network.Network
import com.kavi.pbc.droid.network.model.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AppointmentRemoteRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val network: Network
) {
    private val appointmentServiceApi = network.getRetrofit().create(AppointmentServiceApi::class.java)

    suspend fun createAppointment(appointment: Appointment): ResultWrapper<BaseResponse<String>> {
        return network.invokeApiCall(dispatcher) { appointmentServiceApi.createAppointment(appointment) }
    }

    suspend fun getUserAppointmentList(userId: String): ResultWrapper<BaseResponse<List<Appointment>>> {
        return network.invokeApiCall(dispatcher) { appointmentServiceApi.getUserAppointmentList(userId = userId) }
    }

    suspend fun deleteAppointment(appointmentId: String): ResultWrapper<BaseResponse<String>> {
        return network.invokeApiCall(dispatcher) { appointmentServiceApi.deleteAppointment(appointmentId = appointmentId) }
    }
}