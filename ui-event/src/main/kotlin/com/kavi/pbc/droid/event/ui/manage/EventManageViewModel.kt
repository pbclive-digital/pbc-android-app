package com.kavi.pbc.droid.event.ui.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.event.data.model.EventMangeMode
import com.kavi.pbc.droid.event.data.repository.remote.EventRemoteRepository
import com.kavi.pbc.droid.network.model.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventManageViewModel @Inject constructor(
    private val remoteDataSource: EventRemoteRepository
): ViewModel() {

    private val _activeEventList = MutableStateFlow<MutableList<Event>>(mutableListOf())
    val activeEventList: StateFlow<List<Event>> = _activeEventList

    private val _draftEventList = MutableStateFlow<MutableList<Event>>(mutableListOf())
    val draftEventList: StateFlow<List<Event>> = _draftEventList

    fun fetchActiveEvents(isForceFetch: Boolean = false) {
        if (_activeEventList.value.isEmpty() || isForceFetch) {
            viewModelScope.launch {
                when (val response = remoteDataSource.getUpcomingEvents()) {
                    is ResultWrapper.NetworkError -> {}
                    is ResultWrapper.HttpError -> {}
                    is ResultWrapper.UnAuthError -> {}
                    is ResultWrapper.Success -> {
                        response.value.body?.let {
                            _activeEventList.value = it
                        }
                    }
                }
            }
        }
    }

    fun fetchDraftEvents(isForceFetch: Boolean = false) {
        if (_draftEventList.value.isEmpty() || isForceFetch) {
            viewModelScope.launch {
                when (val response = remoteDataSource.getDraftEvents()) {
                    is ResultWrapper.NetworkError -> {}
                    is ResultWrapper.HttpError -> {}
                    is ResultWrapper.UnAuthError -> {}
                    is ResultWrapper.Success -> {
                        response.value.body?.let {
                            _draftEventList.value = it
                        }
                    }
                }
            }
        }
    }

    fun publishDraftEvent(eventId: String) {

        val eventFilter = _draftEventList.value.filter { it.id == eventId }

        if (eventFilter.isNotEmpty() && eventFilter.size == 1) {
            viewModelScope.launch {
                when(val response = remoteDataSource.publishDraftEvent(eventId = eventId, event = eventFilter[0])) {
                    is ResultWrapper.NetworkError -> {}
                    is ResultWrapper.HttpError -> {}
                    is ResultWrapper.UnAuthError -> {}
                    is ResultWrapper.Success -> {
                        response.value.body?.let { updatedEvent ->
                            _draftEventList.value = _draftEventList.value
                                .filterNot { it.id == eventId }
                                .toMutableList()

                            _activeEventList.update { currentList ->
                                (currentList + updatedEvent) as MutableList<Event>
                            }
                        }
                    }
                }
            }
        }
    }

    fun deleteGivenEvent(eventId: String, eventMode: EventMangeMode) {
        if (eventId.isNotEmpty()) {
            viewModelScope.launch {
                when (remoteDataSource.deleteEvent(eventId = eventId)) {
                    is ResultWrapper.NetworkError -> {}
                    is ResultWrapper.HttpError -> {}
                    is ResultWrapper.UnAuthError -> {}
                    is ResultWrapper.Success -> {
                        when(eventMode) {
                            EventMangeMode.DRAFT -> {
                                _draftEventList.value = _draftEventList.value
                                    .filterNot { it.id == eventId }
                                    .toMutableList()
                            }
                            EventMangeMode.ACTIVE -> {
                                _activeEventList.value = _activeEventList.value
                                    .filterNot { it.id == eventId }
                                    .toMutableList()
                            }
                            EventMangeMode.UNSELECTED -> {
                                // Do Nothing
                            }
                        }
                    }
                }
            }
        }
    }
}