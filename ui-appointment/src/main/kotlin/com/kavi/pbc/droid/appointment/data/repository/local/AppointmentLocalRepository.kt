package com.kavi.pbc.droid.appointment.data.repository.local

import com.kavi.pbc.droid.data.dto.Config
import com.kavi.pbc.droid.data.dto.appointment.Appointment
import com.kavi.pbc.droid.data.dto.appointment.AppointmentRequest
import com.kavi.pbc.droid.lib.datastore.AppInMemoryStore
import com.kavi.pbc.droid.lib.datastore.DataKey
import java.util.UUID
import javax.inject.Inject

class AppointmentLocalRepository @Inject constructor(
    val inMemoryStore: AppInMemoryStore
) {
    fun retrieveResidenceMonkList(): Result<Config> {
        return inMemoryStore.retrieveValue<Config>(DataKey.APP_CONFIG)
    }

    fun setModifyingAppointment(appointment: Appointment): String {
        val randomAppointmentKey = UUID.randomUUID().toString()
        inMemoryStore.storeValue(randomAppointmentKey, appointment)
        return randomAppointmentKey
    }

    fun getModifyingAppointment(tempAppointmentKey: String): Result<Appointment> {
        val appointment = inMemoryStore.retrieveValue<Appointment>(key = tempAppointmentKey)
        return appointment
    }

    fun setModifyingAppointmentRequest(appointmentReq: AppointmentRequest): String {
        val randomAppointmentReqKey = UUID.randomUUID().toString()
        inMemoryStore.storeValue(randomAppointmentReqKey, appointmentReq)
        return randomAppointmentReqKey
    }

    fun getModifyingAppointmentRequest(tempAppointmentReqKey: String): Result<AppointmentRequest> {
        val appointmentReq = inMemoryStore.retrieveValue<AppointmentRequest>(key = tempAppointmentReqKey)
        return appointmentReq
    }

    fun setNewlyCreatedAppointment(appointment: Appointment) {
        inMemoryStore.storeValue(AppointmentDataKey.NEWLY_CREATED_APPOINTMENT, appointment)
    }

    fun getNewlyCreatedAppointment(): Result<Appointment> {
        return inMemoryStore.retrieveValue<Appointment>(AppointmentDataKey.NEWLY_CREATED_APPOINTMENT)
    }

    fun clearNewlyCreatedAppointment() {
        inMemoryStore.cleanValue(AppointmentDataKey.NEWLY_CREATED_APPOINTMENT)
    }
}