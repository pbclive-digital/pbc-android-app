package com.kavi.pbc.droid.event.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.event.data.repository.remote.EventRemoteRepository
import com.kavi.pbc.droid.network.model.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val remoteDataSource: EventRemoteRepository
): ViewModel() {

    private val _upcomingEventList = MutableStateFlow<List<Event>>(mutableListOf())
    val upcomingEventList: StateFlow<List<Event>> = _upcomingEventList

    private val _pastEventList = MutableStateFlow<List<Event>>(mutableListOf())
    val pastEventList: StateFlow<List<Event>> = _pastEventList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchUpcomingEvents(isForceFetch: Boolean = false) {
        if (_upcomingEventList.value.isEmpty() || isForceFetch) {
            _isLoading.value = true
            viewModelScope.launch {
                when (val response = remoteDataSource.getUpcomingEvents()) {
                    is ResultWrapper.NetworkError -> {
                        _isLoading.value = false
                    }
                    is ResultWrapper.HttpError -> {
                        _isLoading.value = false
                    }
                    is ResultWrapper.UnAuthError -> {
                        _isLoading.value = false
                    }
                    is ResultWrapper.Success -> {
                        _isLoading.value = false
                        response.value.body?.let {
                            _upcomingEventList.value = it
                        }
                    }
                }
            }
        }
    }

    fun fetchPastEvents(isForceFetch: Boolean = false) {
        if (_pastEventList.value.isEmpty() || isForceFetch) {
            viewModelScope.launch {
                _isLoading.value = true
                when (val response = remoteDataSource.getPastEvents()) {
                    is ResultWrapper.NetworkError -> {
                        _isLoading.value = false
                    }
                    is ResultWrapper.HttpError -> {
                        _isLoading.value = false
                    }
                    is ResultWrapper.UnAuthError -> {
                        _isLoading.value = false
                    }
                    is ResultWrapper.Success -> {
                        _isLoading.value = false
                        response.value.body?.let {
                            _pastEventList.value = it
                        }
                    }
                }
            }
        }
    }
}