package com.kavi.pbc.droid.event.ui.create

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.data.dto.event.PotluckItem
import com.kavi.pbc.droid.network.session.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class EventCreateViewModel @Inject constructor(): ViewModel() {

    private var _newEvent = MutableStateFlow(Event(creator = Session.user!!.id!!))
    val newEvent: StateFlow<Event> = _newEvent

    private var _potluckItemList = MutableStateFlow<MutableList<PotluckItem>>(mutableListOf())
    val potluckItemList: StateFlow<List<PotluckItem>> = _potluckItemList

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
        _potluckItemList.value.add(potluckItem)
    }

    fun removePotluckItem(potluckItem: PotluckItem) {
        _potluckItemList.value.remove(potluckItem)
    }

    fun updateName(name: String) {
        _newEvent.value.name = name
    }

    fun updateDescription(description: String) {
        _newEvent.value.description = description
    }

    fun updateVenue(venue: String) {
        _newEvent.value.venue = venue
    }

    fun updateDate(date: Long?) {
        date?.let {
            _newEvent.value.eventDate = it
        }
    }

    fun updateStartTime(startTime: String) {
        _newEvent.value.startTime = startTime
    }

    fun updateEndTime(endTime: String) {
        _newEvent.value.endTime = endTime
    }
}