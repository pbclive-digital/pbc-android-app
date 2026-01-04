package com.kavi.pbc.droid.event.ui.selected

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.data.dto.event.potluck.EventPotluck
import com.kavi.pbc.droid.data.dto.event.potluck.EventPotluckContributor
import com.kavi.pbc.droid.data.dto.event.potluck.EventPotluckItem
import com.kavi.pbc.droid.data.dto.event.register.EventRegistration
import com.kavi.pbc.droid.data.dto.event.register.EventRegistrationItem
import com.kavi.pbc.droid.data.dto.event.signup.EventSignUpSheet
import com.kavi.pbc.droid.data.dto.event.signup.SheetContributor
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

    private val _eventSignUpSheetData = MutableStateFlow(EventSignUpSheet(""))
    val eventSignUpSheetData: StateFlow<EventSignUpSheet> = _eventSignUpSheetData

    private val _eventPotluckData = MutableStateFlow(EventPotluck("", mutableListOf()))
    val eventPotluckData: StateFlow<EventPotluck> = _eventPotluckData

    fun revokeActionFunctionStatus() {
        _actionFunctionStatus.value = false
    }

    fun setGivenEvent(givenEvent: Event) {
        _givenEvent.value = givenEvent

        if (_givenEvent.value.registrationRequired)
            fetchRegistrationDetails()

        if (_givenEvent.value.potluckAvailable)
            fetchPotluckDetails()

        if (_givenEvent.value.signUpSheetAvailable)
            fetchSignUpSheetDetails()
    }

    fun checkedCurrentUserContribution(potluckItem: EventPotluckItem): Int {
        Session.user?.let { currentUser ->
            val contribution = potluckItem.contributorList.filter { contributor -> contributor.contributorId == currentUser.id }
            return contribution.size
        }
        return 0
    }

    fun signUpForPotluckItem(potluckItem: EventPotluckItem, onComplete: (isSuccess: Boolean)-> Unit) {
        Session.user?.let {
            val eventPotluckContributor = EventPotluckContributor(
                contributorId = it.id!!,
                contributorName = "${it.firstName} ${it.lastName}",
                contributorContactNumber = it.phoneNumber ?: run { "" }
            )

            viewModelScope.launch {
                when(val response = remoteDataSource
                    .signUpToPotluck(eventId =_givenEvent.value.id!!, potluckItemId = potluckItem.itemId, eventPotluckContributor)) {
                    is ResultWrapper.NetworkError -> {
                        onComplete.invoke(false)
                    }
                    is ResultWrapper.HttpError -> {
                        onComplete.invoke(false)
                    }
                    is ResultWrapper.UnAuthError -> {
                        onComplete.invoke(false)
                    }
                    is ResultWrapper.Success -> {
                        response.value.body?.let { updatedEventPotluck ->
                            _eventPotluckData.value = updatedEventPotluck
                            onComplete.invoke(true)
                        }
                    }
                }
            }
        }
    }

    fun signOutFromPotluckItem(potluckItem: EventPotluckItem, onComplete: (isSuccess: Boolean)-> Unit) {
        Session.user?.let {
            viewModelScope.launch {
                when(val response = remoteDataSource
                    .signOutFromPotluck(eventId =_givenEvent.value.id!!, potluckItemId = potluckItem.itemId, contributorId = it.id!!)) {
                    is ResultWrapper.NetworkError -> {
                        onComplete.invoke(false)
                    }
                    is ResultWrapper.HttpError -> {
                        onComplete.invoke(false)
                    }
                    is ResultWrapper.UnAuthError -> {
                        onComplete.invoke(false)
                    }
                    is ResultWrapper.Success -> {
                        response.value.body?.let { updatedEventPotluck ->
                            _eventPotluckData.value = updatedEventPotluck
                            onComplete.invoke(true)
                        }
                    }
                }
            }
        }
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

    fun isCurrentUserSignUpToSignUpSheet(sheetId: String): Boolean {
        val filteredSignUpSheetList = _eventSignUpSheetData.value.signUpSheetItemList.filter { it.sheetId == sheetId }
        return if (filteredSignUpSheetList.isNotEmpty()) {
            val selectedSheet = filteredSignUpSheetList[0]
            val filtered = selectedSheet.contributorList.filter { it.contributorId == Session.user?.id }
            filtered.isNotEmpty()
        } else {
            false
        }
    }

    fun remainingSignUpCountInSignUpSheet(sheetId: String): Int {
        val filteredSignUpSheetList = _eventSignUpSheetData.value.signUpSheetItemList.filter { it.sheetId == sheetId }
        var remainingCount = 0
        if (filteredSignUpSheetList.isNotEmpty()) {
            val selectedSignUpSheet = filteredSignUpSheetList[0]
            remainingCount = selectedSignUpSheet.availableCount - selectedSignUpSheet.contributorList.size
        }

        return remainingCount
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

    fun signUpToSheet(sheetId: String) {
        Session.user?.let { sessionUser ->
            val sheetContributor = SheetContributor(
                sessionUser.id!!, "${sessionUser.firstName!!} ${sessionUser.lastName!!}", sessionUser.phoneNumber
            )

            viewModelScope.launch {
                when(val response = remoteDataSource.signUpToSelectedSignUpSheet(
                    _givenEvent.value.id!!, sheetId = sheetId, contributor = sheetContributor
                )) {
                    is ResultWrapper.NetworkError -> {}
                    is ResultWrapper.HttpError -> {}
                    is ResultWrapper.UnAuthError -> {}
                    is ResultWrapper.Success -> {
                        response.value.body?.let {
                            _eventSignUpSheetData.value = it
                            _actionFunctionStatus.value = true
                        }
                    }
                }
            }
        }
    }

    fun signOutFromSheet(sheetId: String) {
        Session.user?.let { sessionUser ->
            viewModelScope.launch {
                when(val response = remoteDataSource.signOutFromSelectedSignUpSheet(
                    _givenEvent.value.id!!, sheetId = sheetId, contributorId = sessionUser.id!!)) {
                    is ResultWrapper.NetworkError -> {}
                    is ResultWrapper.HttpError -> {}
                    is ResultWrapper.UnAuthError -> {}
                    is ResultWrapper.Success -> {
                        response.value.body?.let {
                            _eventSignUpSheetData.value = it
                            _actionFunctionStatus.value = true
                        }
                    }
                }
            }
        }
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

    private fun fetchPotluckDetails() {
        viewModelScope.launch {
            when(val response = remoteDataSource.getEventPotluck(_givenEvent.value.id!!)) {
                is ResultWrapper.NetworkError -> {}
                is ResultWrapper.HttpError -> {}
                is ResultWrapper.UnAuthError -> {}
                is ResultWrapper.Success -> {
                    response.value.body?.let {
                        _eventPotluckData.value = it
                    }
                }
            }
        }
    }

    private fun fetchSignUpSheetDetails() {
        viewModelScope.launch {
            when(val response = remoteDataSource.getSignUpSheetList(_givenEvent.value.id!!)) {
                is ResultWrapper.NetworkError -> {}
                is ResultWrapper.HttpError -> {}
                is ResultWrapper.UnAuthError -> {}
                is ResultWrapper.Success -> {
                    response.value.body?.let {
                        _eventSignUpSheetData.value = it
                    }
                }
            }
        }
    }
}