package com.kavi.pbc.droid.event.ui.selected

import androidx.lifecycle.ViewModel
import com.kavi.pbc.droid.event.data.repository.remote.EventRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class EventSelectedViewModel @Inject constructor(
    private val remoteDataSource: EventRemoteRepository
): ViewModel() {
    private val _registrationStatus = MutableStateFlow(false)
    val registrationStatus: StateFlow<Boolean> = _registrationStatus

    private val _authRequiredStatus = MutableStateFlow(false)
    val authRequiredStatus: StateFlow<Boolean> = _authRequiredStatus

    fun registerToEvent() {

    }

    fun registerToPotluckItem() {

    }
}