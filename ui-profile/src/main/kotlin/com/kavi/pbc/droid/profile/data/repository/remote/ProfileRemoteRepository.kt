package com.kavi.pbc.droid.profile.data.repository.remote

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.user.User
import com.kavi.pbc.droid.network.Network
import com.kavi.pbc.droid.network.model.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ProfileRemoteRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val network: Network
) {
    private val profileServiceApi = network.getRetrofit().create(ProfileServiceApi::class.java)

    suspend fun deleteUserAccount(userId: String): ResultWrapper<BaseResponse<String>> {
        return network.invokeApiCall(dispatcher) { profileServiceApi.deleteUserAccount(userId = userId) }
    }

    suspend fun updateUserAccount(userId: String, user: User): ResultWrapper<BaseResponse<User>> {
        return network.invokeApiCall(dispatcher) { profileServiceApi.updateUserAccount(userId = userId, user = user) }
    }
}