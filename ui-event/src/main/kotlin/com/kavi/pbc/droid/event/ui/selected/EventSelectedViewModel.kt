package com.kavi.pbc.droid.event.ui.selected

import androidx.lifecycle.ViewModel
import com.kavi.pbc.droid.event.data.repository.remote.EventRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventSelectedViewModel @Inject constructor(
    private val remoteDataSource: EventRemoteRepository
): ViewModel() {

}