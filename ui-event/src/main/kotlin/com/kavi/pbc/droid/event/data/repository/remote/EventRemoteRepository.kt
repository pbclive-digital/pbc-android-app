package com.kavi.pbc.droid.event.data.repository.remote

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.data.dto.event.register.EventRegistration
import com.kavi.pbc.droid.data.dto.event.register.EventRegistrationItem
import com.kavi.pbc.droid.network.Network
import com.kavi.pbc.droid.network.model.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.MultipartBody
import javax.inject.Inject

class EventRemoteRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val network: Network
) {
    private val eventServiceApi = network.getRetrofit().create(EventServiceApi::class.java)

    suspend fun getUpcomingEvents(): ResultWrapper<BaseResponse<MutableList<Event>>> {
        return network.invokeApiCall(dispatcher) { eventServiceApi.getUpcomingEvents() }
    }

    suspend fun getPastEvents(): ResultWrapper<BaseResponse<MutableList<Event>>> {
        return network.invokeApiCall(dispatcher) { eventServiceApi.getPastEvents() }
    }

    suspend fun getDraftEvents(): ResultWrapper<BaseResponse<MutableList<Event>>> {
        return network.invokeApiCall(dispatcher) { eventServiceApi.getDraftEvents() }
    }

    suspend fun createEvent(event: Event): ResultWrapper<BaseResponse<String>> {
        return network.invokeApiCall(dispatcher) { eventServiceApi.createNewEvent(event) }
    }

    suspend fun updateEvent(eventId: String, event: Event): ResultWrapper<BaseResponse<Event>> {
        return network.invokeApiCall(dispatcher) { eventServiceApi.updateEvent(eventId, event) }
    }

    suspend fun uploadEventImage(eventName: String, imageFile: MultipartBody.Part): ResultWrapper<BaseResponse<String>> {
        return network.invokeApiCall(dispatcher) { eventServiceApi.uploadEventImage(eventName, imageFile) }
    }

    suspend fun publishDraftEvent(eventId: String, event: Event): ResultWrapper<BaseResponse<Event>> {
        return network.invokeApiCall(dispatcher) { eventServiceApi.publishDraftEvent(eventId = eventId, event = event) }
    }

    suspend fun deleteEvent(eventId: String): ResultWrapper<BaseResponse<String>> {
        return network.invokeApiCall(dispatcher) { eventServiceApi.deleteEvent(eventId = eventId) }
    }

    suspend fun getEventRegistration(eventId: String): ResultWrapper<BaseResponse<EventRegistration>> {
        return network.invokeApiCall(dispatcher) { eventServiceApi.getEventRegistration(eventId = eventId) }
    }

    suspend fun registerToEvent(eventId: String, eventRegistrationItem: EventRegistrationItem): ResultWrapper<BaseResponse<String>> {
        return network.invokeApiCall(dispatcher) { eventServiceApi.registerToEvent(eventId = eventId, eventRegistrationItem = eventRegistrationItem) }
    }

    suspend fun unregisterFromEvent(eventId: String, userId: String): ResultWrapper<BaseResponse<String>> {
        return network.invokeApiCall(dispatcher) { eventServiceApi.unregisterFromEvent(eventId = eventId, userId = userId) }
    }
}