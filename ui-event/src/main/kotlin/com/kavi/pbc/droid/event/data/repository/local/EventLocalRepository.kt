package com.kavi.pbc.droid.event.data.repository.local

import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.lib.datastore.AppInMemoryStore
import java.util.UUID
import javax.inject.Inject

class EventLocalRepository @Inject constructor(
    private val inMemoryStore: AppInMemoryStore
) {
    fun setSelectedEvent(event: Event): String {
        val randomEventKey = UUID.randomUUID().toString()
        inMemoryStore.storeValue(randomEventKey, event)
        return randomEventKey
    }
    fun getSelectedEvent(tempEventKey: String): Result<Event> {
        val event = inMemoryStore.retrieveValue<Event>(key = tempEventKey)
        inMemoryStore.cleanValue(key = tempEventKey)
        return event
    }

    fun setModifyingEvent(event: Event): String {
        val randomEventKey = UUID.randomUUID().toString()
        inMemoryStore.storeValue(randomEventKey, event)
        return randomEventKey
    }

    fun getModifyingEvent(tempEventKey: String): Result<Event> {
        val event = inMemoryStore.retrieveValue<Event>(key = tempEventKey)
        return event
    }
}