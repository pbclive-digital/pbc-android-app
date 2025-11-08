package com.kavi.pbc.droid.user.data.repository

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.user.User
import com.kavi.pbc.droid.network.Network
import com.kavi.pbc.droid.network.model.ResultWrapper
import com.kavi.pbc.droid.user.data.model.UserRoleUpdateReq
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UserRemoteRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val network: Network
) {

    private val userServiceApi = network.getRetrofit().create(UserServiceApi::class.java)

    suspend fun getUserByEmail(email: String): ResultWrapper<BaseResponse<User>> {
        return network.invokeApiCall(dispatcher) { userServiceApi.getUserByEmail(email = email) }
    }

    suspend fun getUserByName(name: String): ResultWrapper<BaseResponse<MutableList<User>>> {
        return network.invokeApiCall(dispatcher) { userServiceApi.getUserByName(name = name) }
    }

    suspend fun modifyUserType(userRoleUpdateReq: UserRoleUpdateReq): ResultWrapper<BaseResponse<User>> {
        return network.invokeApiCall(dispatcher) { userServiceApi.modifyUserType(userRoleUpdateReq = userRoleUpdateReq) }
    }
}