package com.kavi.pbc.droid.event.data.repository.local

import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.lib.datastore.AppInMemoryStore
import javax.inject.Inject

class EventLocalRepository @Inject constructor(
    private val inMemoryStore: AppInMemoryStore
) {
    fun getSelectedEvent(tempEventKey: String): Result<Event> {
        val event = inMemoryStore.retrieveValue<Event>(key = tempEventKey)
        inMemoryStore.cleanValue(key = tempEventKey)
        return event
    }
}