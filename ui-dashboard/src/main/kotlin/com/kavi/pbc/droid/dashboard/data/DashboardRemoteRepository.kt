package com.kavi.pbc.droid.dashboard.data

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.network.Network
import com.kavi.pbc.droid.network.model.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DashboardRemoteRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val network: Network
) {

    private val dashboardServiceApi = network.getRetrofit().create(DashboardServiceApi::class.java)

    suspend fun getDashboardEvents(): ResultWrapper<BaseResponse<List<Event>>> {
        return network.invokeApiCall(dispatcher) { dashboardServiceApi.getDashboardEvents() }
    }
}