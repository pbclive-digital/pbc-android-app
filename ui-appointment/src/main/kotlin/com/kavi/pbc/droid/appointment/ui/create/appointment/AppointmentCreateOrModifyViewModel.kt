package com.kavi.pbc.droid.appointment.ui.create.appointment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.appointment.data.model.AppointmentCreationStatus
import com.kavi.pbc.droid.appointment.data.repository.local.AppointmentLocalRepository
import com.kavi.pbc.droid.appointment.data.repository.remote.AppointmentRemoteRepository
import com.kavi.pbc.droid.data.dto.appointment.Appointment
import com.kavi.pbc.droid.data.dto.user.User
import com.kavi.pbc.droid.network.model.ResultWrapper
import com.kavi.pbc.droid.network.session.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.text.ifEmpty

@HiltViewModel
class AppointmentCreateOrModifyViewModel @Inject constructor(
    val localRepository: AppointmentLocalRepository,
    val remoteRepository: AppointmentRemoteRepository
): ViewModel() {

    private val _appointmentCreationStatus = MutableStateFlow(AppointmentCreationStatus.NONE)
    val appointmentCreationStatus: StateFlow<AppointmentCreationStatus> = _appointmentCreationStatus

    private val _residenceMonkList = MutableStateFlow<List<String>>(mutableListOf())
    val residenceMonkList: StateFlow<List<String>> = _residenceMonkList

    private val _newAppointment = MutableStateFlow(Appointment(
        user = Session.user!!,
        userId = Session.user!!.id!!
    ))
    val newAppointment: StateFlow<Appointment> = _newAppointment

    val monkMapping = mutableMapOf<String, User>()

    fun setModifyingAppointment(appointmentKey: String) {
        localRepository.getModifyingAppointment(tempAppointmentKey = appointmentKey).onSuccess { appointment ->
            _newAppointment.value = appointment
        }
    }

    fun formatDate(selectedDateMils: Long?): String {
        return selectedDateMils?.let {
            SimpleDateFormat("dd MMM, yyyy", Locale.US).format(Date(it))
        }?: run {
            "Select Event Date".uppercase()
        }
    }

    fun formatTime(hour: Int, minute: Int): String {
        return String.format(Locale.US, "%02d:%02d", hour, minute)
    }

    fun getResidentMonkList() {
        val monkList = mutableListOf<String>()
        localRepository.retrieveResidenceMonkList()
            .onSuccess { config ->
                config.residentMonkList.forEachIndexed { index, monk ->
                    val monkDisplayName = "${index +1} - Bhanthe ${monk.lastName}"
                    monkMapping[monkDisplayName] = monk
                    monkList.add(monkDisplayName)
                }
            }

        _residenceMonkList.value = monkList
    }

    fun createNewAppointment() {
        Session.user?.let {
            _appointmentCreationStatus.value = AppointmentCreationStatus.PENDING
            viewModelScope.launch {
                when (val response = remoteRepository.createAppointment(_newAppointment.value)) {
                    is ResultWrapper.NetworkError,
                    is ResultWrapper.HttpError -> {
                        _appointmentCreationStatus.value = AppointmentCreationStatus.FAILURE
                    }
                    is ResultWrapper.UnAuthError -> {
                        _appointmentCreationStatus.value = AppointmentCreationStatus.UNAUTHENTICATE
                    }
                    is ResultWrapper.Success -> {
                        response.value.body?.let {
                            _appointmentCreationStatus.value = AppointmentCreationStatus.SUCCESS
                        }
                    }
                }
            }
        }
    }

    fun updateTitle(title: String) {
        _newAppointment.value.title = title
    }

    fun updateReason(reason: String) {
        _newAppointment.value.reason = reason
    }

    fun updateSelectedMonk(selectedMonkName: String) {
        monkMapping[selectedMonkName]?.let {
            _newAppointment.value.selectedMonkId = it.id!!
            _newAppointment.value.selectedMonk = it
        }
    }

    fun updateDate(date: Long?) {
        date?.let {
            _newAppointment.value.date = it
        }
    }

    fun updateTime(time: String) {
        _newAppointment.value.time = time
    }

    fun getInitialAppointmentDate(): String {
        return if (_newAppointment.value.date.toInt() == 0)
            "SELECT DATE"
        else
            _newAppointment.value.getFormatDate()
    }

    fun getInitialTime(): String {
        return _newAppointment.value.time.ifEmpty {
            "TIME"
        }
    }

    fun getInitialSelectedMonk(): String {
        return _newAppointment.value.selectedMonk?.let {
            "Bhanthe ${it.lastName}"
        }?: run {
            ""
        }
    }
}