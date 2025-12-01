package com.kavi.pbc.droid.appointment.data.repository.local

import com.kavi.pbc.droid.data.dto.Config
import com.kavi.pbc.droid.data.dto.appointment.Appointment
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
}