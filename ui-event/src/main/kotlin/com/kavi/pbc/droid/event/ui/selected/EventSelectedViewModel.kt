package com.kavi.pbc.droid.event.ui.selected

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.data.dto.event.register.EventRegistration
import com.kavi.pbc.droid.data.dto.event.register.EventRegistrationItem
import com.kavi.pbc.droid.event.data.repository.remote.EventRemoteRepository
import com.kavi.pbc.droid.network.model.ResultWrapper
import com.kavi.pbc.droid.network.session.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventSelectedViewModel @Inject constructor(
    private val remoteDataSource: EventRemoteRepository
): ViewModel() {
    private val _actionFunctionStatus = MutableStateFlow(false)
    val actionFunctionStatus: StateFlow<Boolean> = _actionFunctionStatus

    private val _authRequiredStatus = MutableStateFlow(false)
    val authRequiredStatus: StateFlow<Boolean> = _authRequiredStatus

    private val _givenEvent = MutableStateFlow(Event(creator = ""))
    val givenEvent: StateFlow<Event> = _givenEvent

    private val _eventRegistrationData = MutableStateFlow(EventRegistration("", 0))
    val eventRegistrationData: StateFlow<EventRegistration> = _eventRegistrationData

    fun revokeActionFunctionStatus() {
        _actionFunctionStatus.value = false
    }

    fun setGivenEvent(givenEvent: Event) {
        _givenEvent.value = givenEvent

        if (_givenEvent.value.registrationRequired)
            fetchRegistrationDetails()
    }

    fun remainingSeatCountAvailable(): Int {
        var remainingCount = 0
        if (_givenEvent.value.registrationRequired) {
            _givenEvent.value.openSeatCount?.let {
                remainingCount = it - _eventRegistrationData.value.registrationList.size
            }
        }

        return remainingCount
    }

    fun isCurrentUserRegistered(): Boolean {
        return if (_givenEvent.value.registrationRequired) {
            val filtered = _eventRegistrationData.value.registrationList.filter { it.participantUserId == Session.user?.id }
            filtered.isNotEmpty()
        } else {
            false
        }
    }

    fun registerToEvent() {
        Session.user?.let { sessionUser ->
            val eventRegistrationItem = EventRegistrationItem(
                participantUserId = sessionUser.id!!,
                participantName = "${sessionUser.firstName!!} ${sessionUser.lastName!!}",
                participantAddress = sessionUser.address,
                participantContactNumber = sessionUser.phoneNumber
            )

            viewModelScope.launch {
                when(val response = remoteDataSource.registerToEvent(_givenEvent.value.id!!, eventRegistrationItem = eventRegistrationItem)) {
                    is ResultWrapper.NetworkError -> {}
                    is ResultWrapper.HttpError -> {}
                    is ResultWrapper.UnAuthError -> {}
                    is ResultWrapper.Success -> {
                        response.value.body?.let {
                            _eventRegistrationData.value = it
                            _actionFunctionStatus.value = true
                        }
                    }
                }
            }
        }
    }

    fun unregisterFromEvent() {
        Session.user?.let { sessionUser ->
            viewModelScope.launch {
                when(val response = remoteDataSource.unregisterFromEvent(_givenEvent.value.id!!, userId = sessionUser.id!!)) {
                    is ResultWrapper.NetworkError -> {}
                    is ResultWrapper.HttpError -> {}
                    is ResultWrapper.UnAuthError -> {}
                    is ResultWrapper.Success -> {
                        response.value.body?.let {
                            _eventRegistrationData.value = it
                            _actionFunctionStatus.value = true
                        }
                    }
                }
            }
        }
    }

    fun registerToPotluckItem() {

    }

    private fun fetchRegistrationDetails() {
        viewModelScope.launch {
            when(val response = remoteDataSource.getEventRegistration(_givenEvent.value.id!!)) {
                is ResultWrapper.NetworkError -> {}
                is ResultWrapper.HttpError -> {}
                is ResultWrapper.UnAuthError -> {}
                is ResultWrapper.Success -> {
                    response.value.body?.let {
                        _eventRegistrationData.value = it
                    }
                }
            }
        }
    }
}