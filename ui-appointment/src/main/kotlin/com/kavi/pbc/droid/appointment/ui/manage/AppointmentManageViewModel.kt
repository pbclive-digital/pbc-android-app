package com.kavi.pbc.droid.appointment.ui.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.appointment.data.model.AppointmentDeleteStatus
import com.kavi.pbc.droid.appointment.data.repository.remote.AppointmentRemoteRepository
import com.kavi.pbc.droid.data.dto.appointment.Appointment
import com.kavi.pbc.droid.network.model.ResultWrapper
import com.kavi.pbc.droid.network.session.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentManageViewModel @Inject constructor(
    private val appointmentRemoteRepository: AppointmentRemoteRepository
): ViewModel() {

    private val _userAppointmentList = MutableStateFlow<List<Appointment>>(mutableListOf())
    val userAppointmentList: StateFlow<List<Appointment>> = _userAppointmentList

    private val _appointmentDeleteStatus = MutableStateFlow(AppointmentDeleteStatus.NONE)
    val appointmentDeleteStatus: StateFlow<AppointmentDeleteStatus> = _appointmentDeleteStatus

    fun fetchAppointmentList() {
        Session.user?.id?.let { userId ->
            viewModelScope.launch {
                when(val response = appointmentRemoteRepository.getUserAppointmentList(userId = userId)) {
                    is ResultWrapper.NetworkError -> {}
                    is ResultWrapper.HttpError -> {}
                    is ResultWrapper.UnAuthError -> {}
                    is ResultWrapper.Success -> {
                        response.value.body?.let {
                            _userAppointmentList.value = it
                        }
                    }
                }
            }
        }
    }

    fun deleteAppointment(appointmentId: String) {
        _appointmentDeleteStatus.value = AppointmentDeleteStatus.PENDING
        viewModelScope.launch {
            when(val response = appointmentRemoteRepository.deleteAppointment(appointmentId = appointmentId)) {
                is ResultWrapper.NetworkError, is ResultWrapper.HttpError,
                is ResultWrapper.UnAuthError -> {
                    _appointmentDeleteStatus.value = AppointmentDeleteStatus.FAILURE
                }
                is ResultWrapper.Success -> {
                    response.value.body?.let {
                        // Update the appointment list by removing deleted item
                        _userAppointmentList.value = _userAppointmentList.value
                            .filterNot { it.id == appointmentId }
                            .toMutableList()

                        _appointmentDeleteStatus.value = AppointmentDeleteStatus.SUCCESS
                    }
                }
            }
        }
    }
}