package com.kavi.pbc.droid.event.ui.create

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.data.dto.event.EventType
import com.kavi.pbc.droid.data.dto.event.PotluckItem
import com.kavi.pbc.droid.data.dto.event.VenueType
import com.kavi.pbc.droid.event.data.repository.local.EventLocalRepository
import com.kavi.pbc.droid.event.data.repository.remote.EventRemoteRepository
import com.kavi.pbc.droid.event.util.FilePickerUtil
import com.kavi.pbc.droid.network.model.ResultWrapper
import com.kavi.pbc.droid.network.session.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class EventCreateViewModel @Inject constructor(
    private val localDataSource: EventLocalRepository,
    private val remoteDataSource: EventRemoteRepository
): ViewModel() {

    private var _newEvent = MutableStateFlow(Event(creator = Session.user!!.id!!))
    val newEvent: StateFlow<Event> = _newEvent

    private var _potluckItemList = MutableStateFlow<MutableList<PotluckItem>>(mutableListOf())
    val potluckItemList: StateFlow<List<PotluckItem>> = _potluckItemList

    private var _eventImageUri = MutableStateFlow<Uri?>(null)
    val eventImageUri: StateFlow<Uri?> = _eventImageUri

    private var _eventCreateOrUpdateStatus = MutableStateFlow<Boolean>(false)
    val eventCreateOrUpdateStatus: StateFlow<Boolean> = _eventCreateOrUpdateStatus

    private var eventImageFile: File? = null

    fun setModifyingEvent(eventKey: String) {
        localDataSource.getModifyingEvent(tempEventKey = eventKey).onSuccess { event ->
            _newEvent.value = event
        }
    }

    fun validateFirstPage(): Boolean {
        return !(_newEvent.value.name.isEmpty() || _newEvent.value.description.isEmpty()
                || _newEvent.value.eventType == EventType.DEFAULT
                || _newEvent.value.eventDate.toInt() == 0 || _newEvent.value.startTime.isEmpty() || _newEvent.value.endTime.isEmpty()
                || _newEvent.value.venueType == VenueType.DEFAULT || _newEvent.value.venue?.isEmpty() == true)
    }

    fun validateSecondPage(): Boolean {
        if (_newEvent.value.registrationRequired) {
            _newEvent.value.openSeatCount?.let { seatCount ->
                if (seatCount == 0) {
                    return false
                }
            }?: run {
                return false
            }
        }

        if (_newEvent.value.potluckAvailable) {
            _newEvent.value.potluckItemList?.let { itemList ->
                if (itemList.isEmpty()) {
                    return false
                }
            }?: run {
                return false
            }
        }

        return true
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

    fun addPotluckItem(potluckItem: PotluckItem) {
        _potluckItemList.update { currentList ->
            (currentList + potluckItem) as MutableList<PotluckItem>
        }
        _newEvent.value.potluckItemList = _potluckItemList.value
    }

    fun removePotluckItem(potluckItem: PotluckItem) {
        _potluckItemList.value = _potluckItemList.value
            .filterNot { it == potluckItem }
            .toMutableList()
        _newEvent.value.potluckItemList = _potluckItemList.value
    }

    fun updateEventImageUrl(eventImage: Uri) {
        _eventImageUri.value = eventImage
    }

    fun updateEventImageFile(eventImage: File) {
        eventImageFile = eventImage
    }

    fun updateName(name: String) {
        _newEvent.value.name = name
    }

    fun updateDescription(description: String) {
        _newEvent.value.description = description
    }

    fun updateEventType(eventType: String) {
        when(eventType) {
            EventType.SPECIAL.name -> _newEvent.value.eventType = EventType.SPECIAL
            EventType.BUDDHISM_CLASS.name -> _newEvent.value.eventType = EventType.BUDDHISM_CLASS
            EventType.MEDITATION.name -> _newEvent.value.eventType = EventType.MEDITATION
            EventType.DHAMMA_TALK.name -> _newEvent.value.eventType = EventType.DHAMMA_TALK
        }
    }

    fun getInitialEventType(): String {
        return if (_newEvent.value.eventType == EventType.DEFAULT)
            ""
        else
            _newEvent.value.eventType.name
    }

    fun updateDate(date: Long?) {
        date?.let {
            _newEvent.value.eventDate = it
        }
    }

    fun getInitialEventDate(): String {
        return if (_newEvent.value.eventDate.toInt() == 0)
            "SELECT DATE"
        else
            _newEvent.value.getFormatDate()
    }

    fun updateStartTime(startTime: String) {
        _newEvent.value.startTime = startTime
    }

    fun getInitialStartTime(): String {
        return _newEvent.value.startTime.ifEmpty {
            "FROM"
        }
    }

    fun updateEndTime(endTime: String) {
        _newEvent.value.endTime = endTime
    }

    fun getInitialEndTime(): String {
        return _newEvent.value.endTime.ifEmpty {
            "TO"
        }
    }

    fun updateVenueType(venueType: String) {
        when(venueType) {
            VenueType.PHYSICAL.name -> _newEvent.value.venueType = VenueType.PHYSICAL
            VenueType.VIRTUAL.name -> _newEvent.value.venueType = VenueType.VIRTUAL
        }
    }

    fun getInitialVenueType(): String {
        return if (_newEvent.value.venueType == VenueType.DEFAULT)
            "VENUE TYPE"
        else
            _newEvent.value.venueType.name
    }

    fun updateVenue(venue: String) {
        _newEvent.value.venue = venue
    }

    fun updateRegistrationRequiredFlag(isRegistrationRequired: Boolean) {
        _newEvent.value.registrationRequired = isRegistrationRequired
    }

    fun updateSeatCount(seatCount: Int) {
        _newEvent.value.openSeatCount = seatCount
    }

    fun updatePotluckAvailabilityFlag(isPotluckAvailable: Boolean) {
        _newEvent.value.potluckAvailable = isPotluckAvailable
    }

    fun uploadEventImageAndCreateOrUpdateEvent(isModify: Boolean = false) {
        val imagePartRequest = FilePickerUtil.createMultiPartRequest(eventImageFile)
        val formatedEventName = _newEvent.value.name.replace(" ", "_").replace("-", "_")

        if (imagePartRequest != null) {
            viewModelScope.launch {
                when(val response = remoteDataSource.uploadEventImage(formatedEventName, imagePartRequest)) {
                    is ResultWrapper.NetworkError -> {
                        println("Failed: NetworkError")
                    }
                    is ResultWrapper.HttpError -> {
                        if (isModify) {
                            updateEvent()
                        } else {
                            createNewEvent()
                        }
                    }
                    is ResultWrapper.UnAuthError -> {
                        println("Failed: NetworkError")
                    }
                    is ResultWrapper.Success -> {
                        response.value.body?.let {
                            _newEvent.value.eventImage = it
                            if (isModify) {
                                updateEvent()
                            } else {
                                createNewEvent()
                            }
                        }
                    }
                }
            }
        } else {
            if (isModify) {
                updateEvent()
            } else {
                createNewEvent()
            }
        }
    }

    private fun createNewEvent() {
        viewModelScope.launch {
            when(val response = remoteDataSource.createEvent(_newEvent.value)) {
                is ResultWrapper.NetworkError -> {}
                is ResultWrapper.HttpError -> {}
                is ResultWrapper.UnAuthError -> {}
                is ResultWrapper.Success -> {
                    response.value.body?.let {
                        println("Passed: $it")
                        _eventCreateOrUpdateStatus.value = true
                    }
                }
            }
        }
    }

    private fun updateEvent() {
        viewModelScope.launch {
            when(val response = remoteDataSource.updateEvent(_newEvent.value.id!!, _newEvent.value)) {
                is ResultWrapper.NetworkError -> {}
                is ResultWrapper.HttpError -> {}
                is ResultWrapper.UnAuthError -> {}
                is ResultWrapper.Success -> {
                    response.value.body?.let {
                        println("Passed: $it")
                        _eventCreateOrUpdateStatus.value = true
                    }
                }
            }
        }
    }
}