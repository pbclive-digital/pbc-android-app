package com.kavi.pbc.droid.appointment.ui.create.request

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.appointment.data.model.AppointmentReqCreationStatus
import com.kavi.pbc.droid.appointment.data.repository.local.AppointmentLocalRepository
import com.kavi.pbc.droid.appointment.data.repository.remote.AppointmentRemoteRepository
import com.kavi.pbc.droid.data.dto.appointment.AppointmentRequest
import com.kavi.pbc.droid.data.dto.appointment.AppointmentRequestType
import com.kavi.pbc.droid.data.dto.user.User
import com.kavi.pbc.droid.network.model.ResultWrapper
import com.kavi.pbc.droid.network.session.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.set

@HiltViewModel
class AppointmentRequestCreateOrModifyViewModel @Inject constructor(
    val localRepository: AppointmentLocalRepository,
    val remoteRepository: AppointmentRemoteRepository
): ViewModel() {

    private val _residenceMonkList = MutableStateFlow<List<String>>(mutableListOf())
    val residenceMonkList: StateFlow<List<String>> = _residenceMonkList

    private val _newAppointmentRequest = MutableStateFlow(AppointmentRequest(
        user = Session.user!!,
        userId = Session.user!!.id!!
    ))
    val newAppointmentRequest: StateFlow<AppointmentRequest> = _newAppointmentRequest

    private val _newAppointmentReqCreateStatus = MutableStateFlow(AppointmentReqCreationStatus.NONE)
    val newAppointmentReqCreateStatus: StateFlow<AppointmentReqCreationStatus> = _newAppointmentReqCreateStatus

    val monkMapping = mutableMapOf<String, User>()

    fun setModifyingAppointmentRequest(appointmentReqKey: String) {
        localRepository.getModifyingAppointmentRequest(tempAppointmentReqKey = appointmentReqKey).onSuccess { appointmentRequest ->
            _newAppointmentRequest.value = appointmentRequest
        }
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

    fun createNewAppointmentRequest() {
        Session.user?.let {
            _newAppointmentReqCreateStatus.value = AppointmentReqCreationStatus.PENDING
            viewModelScope.launch {
                when (val response = remoteRepository
                    .createAppointmentRequest(newAppointmentRequest.value)) {
                    is ResultWrapper.NetworkError,
                    is ResultWrapper.HttpError -> {
                        _newAppointmentReqCreateStatus.value = AppointmentReqCreationStatus.FAILURE
                    }
                    is ResultWrapper.UnAuthError -> {
                        _newAppointmentReqCreateStatus.value = AppointmentReqCreationStatus.UNAUTHENTICATE
                    }
                    is ResultWrapper.Success -> {
                        response.value.body?.let {
                            _newAppointmentReqCreateStatus.value = AppointmentReqCreationStatus.SUCCESS
                        }
                    }
                }
            }
        }
    }

    fun updateTitle(title: String) {
        _newAppointmentRequest.value.title = title
    }

    fun updateReason(reason: String) {
        _newAppointmentRequest.value.reason = reason
    }

    fun updateSelectedMonk(selectedMonkName: String) {
        monkMapping[selectedMonkName]?.let {
            _newAppointmentRequest.value.selectedMonkId = it.id!!
            _newAppointmentRequest.value.selectedMonk = it
        }
    }

    fun updateAppointmentType(appointmentType: String) {
        when(appointmentType) {
            AppointmentRequestType.REMOTE.name -> {
                _newAppointmentRequest.value.appointmentReqType = AppointmentRequestType.REMOTE
            }
            AppointmentRequestType.ON_SITE.name -> {
                _newAppointmentRequest.value.appointmentReqType = AppointmentRequestType.ON_SITE
            }
        }
    }

    fun getInitialSelectedMonk(): String {
        return _newAppointmentRequest.value.selectedMonk?.let {
            "Bhanthe ${it.lastName}"
        }?: run {
            ""
        }
    }
}