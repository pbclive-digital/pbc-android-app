package com.kavi.pbc.droid.dashboard.data.repository.remote

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.quote.Quote
import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.data.dto.news.News
import com.kavi.pbc.droid.data.dto.notification.PushTokenRequest
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

    suspend fun getDashboardDailyQuotes(): ResultWrapper<BaseResponse<List<Quote>>> {
        return network.invokeApiCall(dispatcher) { dashboardServiceApi.getDashboardDailyQuotes() }
    }

    suspend fun getDashboardNews(): ResultWrapper<BaseResponse<List<News>>> {
        return network.invokeApiCall(dispatcher) { dashboardServiceApi.getDashboardNews() }
    }

    suspend fun syncPushToken(userId: String, pushTokenReq: PushTokenRequest): ResultWrapper<BaseResponse<String>> {
        return network.invokeApiCall(dispatcher) { dashboardServiceApi
            .syncPushNotificationToken(userId, pushTokenReq)
        }
    }
}