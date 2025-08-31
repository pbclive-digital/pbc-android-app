package com.kavi.pbc.droid.dashboard.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.dashboard.data.repository.remote.DashboardRemoteRepository
import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.network.model.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val remoteDataSource: DashboardRemoteRepository
): ViewModel() {

    private val _dashboardEventList = MutableStateFlow<List<Event>>(mutableListOf())
    val dashboardEventList: StateFlow<List<Event>> = _dashboardEventList

    fun fetchDashboardHomeEvents(isForceFetch: Boolean = false) {
        if (_dashboardEventList.value.isEmpty() || isForceFetch) {
            viewModelScope.launch {
                when (val response = remoteDataSource.getDashboardEvents()) {
                    is ResultWrapper.NetworkError -> {}
                    is ResultWrapper.HttpError -> {}
                    is ResultWrapper.UnAuthError -> {}
                    is ResultWrapper.Success -> {
                        response.value.body?.let {
                            _dashboardEventList.value = it
                        }
                    }
                }
            }
        }
    }
}