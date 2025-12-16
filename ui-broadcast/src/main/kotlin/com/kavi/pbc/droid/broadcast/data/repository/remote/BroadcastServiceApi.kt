package com.kavi.pbc.droid.broadcast.data.repository.remote

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.message.Message
import retrofit2.http.Body
import retrofit2.http.POST

interface BroadcastServiceApi {

    @POST("/broadcast/message")
    suspend fun sendBroadcastMessage(@Body message: Message): BaseResponse<String>
}