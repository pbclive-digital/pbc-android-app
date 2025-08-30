package com.kavi.pbc.droid.event.data.repository.remote

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.network.Network
import com.kavi.pbc.droid.network.model.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class EventRemoteRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val network: Network
) {
    private val eventServiceApi = network.getRetrofit().create(EventServiceApi::class.java)

    suspend fun getUpcomingEvents(): ResultWrapper<BaseResponse<List<Event>>> {
        return network.invokeApiCall(dispatcher) { eventServiceApi.getUpcomingEvents() }
    }

    suspend fun getPastEvents(): ResultWrapper<BaseResponse<List<Event>>> {
        return network.invokeApiCall(dispatcher) { eventServiceApi.getPastEvents() }
    }
}