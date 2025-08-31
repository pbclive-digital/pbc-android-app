package com.kavi.pbc.droid.dashboard.data.repository.local

import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.lib.datastore.AppInMemoryStore
import java.util.UUID
import javax.inject.Inject

class DashboardLocalRepository @Inject constructor(
    private val inMemoryStore: AppInMemoryStore
) {
    fun setSelectedEvent(event: Event): String {
        val randomEventKey = UUID.randomUUID().toString()
        inMemoryStore.storeValue(randomEventKey, event)
        return randomEventKey
    }
}