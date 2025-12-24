package com.kavi.pbc.droid.appointment.ui.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.appointment.data.model.AppointmentDeleteStatus
import com.kavi.pbc.droid.appointment.data.model.AppointmentReqDeleteStatus
import com.kavi.pbc.droid.appointment.data.repository.local.AppointmentLocalRepository
import com.kavi.pbc.droid.appointment.data.repository.remote.AppointmentRemoteRepository
import com.kavi.pbc.droid.data.dto.appointment.Appointment
import com.kavi.pbc.droid.data.dto.appointment.AppointmentRequest
import com.kavi.pbc.droid.data.dto.user.User
import com.kavi.pbc.droid.network.model.ResultWrapper
import com.kavi.pbc.droid.network.session.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentManageViewModel @Inject constructor(
    private val appointmentLocalRepository: AppointmentLocalRepository,
    private val appointmentRemoteRepository: AppointmentRemoteRepository
): ViewModel() {

    private val _eligibleToCreateRequest = MutableStateFlow(false)
    val eligibleToCreateRequest: StateFlow<Boolean> = _eligibleToCreateRequest
    private val _userAppointmentList = MutableStateFlow<List<Appointment>>(mutableListOf())
    val userAppointmentList: StateFlow<List<Appointment>> = _userAppointmentList

    private val _userAppointmentRequestList = MutableStateFlow<List<AppointmentRequest>>(mutableListOf())
    val userAppointmentRequestList: StateFlow<List<AppointmentRequest>> = _userAppointmentRequestList

    private val _appointmentDeleteStatus = MutableStateFlow(AppointmentDeleteStatus.NONE)
    val appointmentDeleteStatus: StateFlow<AppointmentDeleteStatus> = _appointmentDeleteStatus

    private val _appointmentReqDeleteStatus = MutableStateFlow(AppointmentReqDeleteStatus.NONE)
    val appointmentReqDeleteStatus: StateFlow<AppointmentReqDeleteStatus> = _appointmentReqDeleteStatus

    private val _newlyCreatedAppointment = MutableStateFlow(Appointment(user = User(email = "")))
    val newlyCreatedAppointment: StateFlow<Appointment> = _newlyCreatedAppointment

    private val _isNewlyCreatedAppointmentAvailable = MutableStateFlow(false)
    val isNewlyCreatedAppointmentAvailable: StateFlow<Boolean> = _isNewlyCreatedAppointmentAvailable

    fun getRequestCreateEligibility() {
        Session.user?.id?.let { userId ->
            viewModelScope.launch {
                when(val response = appointmentRemoteRepository.getRequestCreateEligibility(userId = userId)) {
                    is ResultWrapper.NetworkError -> {}
                    is ResultWrapper.HttpError -> {}
                    is ResultWrapper.UnAuthError -> {}
                    is ResultWrapper.Success -> {
                        response.value.body?.let {
                            _eligibleToCreateRequest.value = it.allowToCreateRequest
                        }
                    }
                }
            }
        }
    }

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

    fun checkNewlyCreatedAppointment() {
        appointmentLocalRepository.getNewlyCreatedAppointment().onSuccess { appointment ->
            _isNewlyCreatedAppointmentAvailable.value = true
            _newlyCreatedAppointment.value = appointment
            appointmentLocalRepository.clearNewlyCreatedAppointment()

            fetchAppointmentRequestList()
        }.onFailure {
            _isNewlyCreatedAppointmentAvailable.value = false
        }
    }

    fun fetchAppointmentRequestList() {
        Session.user?.id?.let { userId ->
            viewModelScope.launch {
                when(val response = appointmentRemoteRepository.getUserAppointmentRequestList(userId = userId)) {
                    is ResultWrapper.NetworkError -> {}
                    is ResultWrapper.HttpError -> {
                        if (response.code == 404) {
                            _userAppointmentRequestList.value = emptyList()
                        }
                    }
                    is ResultWrapper.UnAuthError -> {}
                    is ResultWrapper.Success -> {
                        response.value.body?.let {
                            _userAppointmentRequestList.value = it
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

    fun deleteAppointmentRequest(appointmentReqId: String) {
        _appointmentReqDeleteStatus.value = AppointmentReqDeleteStatus.PENDING
        viewModelScope.launch {
            when(val response = appointmentRemoteRepository.deleteAppointmentRequest(appointmentReqId = appointmentReqId)) {
                is ResultWrapper.NetworkError, is ResultWrapper.HttpError,
                is ResultWrapper.UnAuthError -> {
                    _appointmentReqDeleteStatus.value = AppointmentReqDeleteStatus.FAILURE
                }
                is ResultWrapper.Success -> {
                    response.value.body?.let {
                        // Update the appointment list by removing deleted item
                        _userAppointmentRequestList.value = _userAppointmentRequestList.value
                            .filterNot { it.id == appointmentReqId }
                            .toMutableList()

                        _appointmentReqDeleteStatus.value = AppointmentReqDeleteStatus.SUCCESS
                    }
                }
            }
        }
    }

    fun resetAppointmentDeleteStatus() {
        _appointmentDeleteStatus.value = AppointmentDeleteStatus.NONE
    }

    fun resetAppointmentReqDeleteStatus() {
        _appointmentReqDeleteStatus.value = AppointmentReqDeleteStatus.NONE
    }
}