package com.kavi.pbc.droid.appointment.data.repository.remote

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.appointment.Appointment
import com.kavi.pbc.droid.data.dto.appointment.AppointmentRequest
import com.kavi.pbc.droid.data.dto.appointment.AppointmentRequestEligibility
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

    suspend fun createAppointmentRequest(appointmentReq: AppointmentRequest): ResultWrapper<BaseResponse<String>> {
        return network.invokeApiCall(dispatcher) { appointmentServiceApi.createAppointmentRequest(appointmentReq) }
    }

    suspend fun getRequestCreateEligibility(userId: String): ResultWrapper<BaseResponse<AppointmentRequestEligibility>> {
        return network.invokeApiCall(dispatcher) { appointmentServiceApi.validateRequestCreationEligibility(userId = userId) }
    }

    suspend fun getUserAppointmentList(userId: String): ResultWrapper<BaseResponse<List<Appointment>>> {
        return network.invokeApiCall(dispatcher) { appointmentServiceApi.getUserAppointmentList(userId = userId) }
    }

    suspend fun getUserAppointmentRequestList(userId: String): ResultWrapper<BaseResponse<List<AppointmentRequest>>> {
        return network.invokeApiCall(dispatcher) { appointmentServiceApi.getUserAppointmentRequestList(userId = userId) }
    }

    suspend fun deleteAppointment(appointmentId: String): ResultWrapper<BaseResponse<String>> {
        return network.invokeApiCall(dispatcher) { appointmentServiceApi.deleteAppointment(appointmentId = appointmentId) }
    }

    suspend fun deleteAppointmentRequest(appointmentReqId: String): ResultWrapper<BaseResponse<String>> {
        return network.invokeApiCall(dispatcher) { appointmentServiceApi.deleteAppointmentRequest(appointmentReqId = appointmentReqId) }
    }
}