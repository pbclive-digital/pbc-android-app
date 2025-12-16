package com.kavi.pbc.droid.broadcast.data.repository.remote

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.message.Message
import com.kavi.pbc.droid.network.Network
import com.kavi.pbc.droid.network.model.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class BroadcastRemoteRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val network: Network
) {
    private val broadcastServiceApi = network.getRetrofit().create(BroadcastServiceApi::class.java)

    suspend fun sendBroadcastMessage(message: Message): ResultWrapper<BaseResponse<String>> {
        return network.invokeApiCall(dispatcher) {
            broadcastServiceApi.sendBroadcastMessage(message = message)
        }
    }
}