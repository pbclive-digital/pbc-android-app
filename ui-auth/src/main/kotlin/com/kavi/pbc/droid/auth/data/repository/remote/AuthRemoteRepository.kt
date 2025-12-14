package com.kavi.pbc.droid.auth.data.repository.remote

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.auth.AuthToken
import com.kavi.pbc.droid.data.dto.user.User
import com.kavi.pbc.droid.network.Network
import com.kavi.pbc.droid.network.model.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AuthRemoteRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val network: Network
) {
    private var authServiceApi: AuthServiceApi = network.getRetrofit().create(AuthServiceApi::class.java)

    suspend fun getUserStatus(email: String, userId: String): ResultWrapper<BaseResponse<String>> {
        return network.invokeApiCall(dispatcher) { authServiceApi.getUserStatus(email = email, userId = userId) }
    }

    suspend fun getUser(userId: String): ResultWrapper<BaseResponse<User>> {
        return network.invokeApiCall(dispatcher) { authServiceApi.getUser(userId) }
    }

    suspend fun requestAuthToken(email: String, userId: String): ResultWrapper<BaseResponse<AuthToken>> {
        return network.invokeApiCall(dispatcher) { authServiceApi.requestAuthToken(email = email, userId = userId) }
    }

    suspend fun registerUser(user: User): ResultWrapper<BaseResponse<String>> {
        return network.invokeApiCall(dispatcher) { authServiceApi.registerUser(user) }
    }

    suspend fun createNewToken(token: AuthToken): ResultWrapper<BaseResponse<AuthToken>> {
        return network.invokeApiCall(dispatcher) { authServiceApi.createNewToken(token) }
    }
}