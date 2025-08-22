package com.kavi.pbc.droid.splash.data.repository

import com.kavi.pbc.droid.data.dto.AppVersionStatus
import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.Config
import com.kavi.pbc.droid.network.Network
import com.kavi.pbc.droid.network.model.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SplashRemoteRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val network: Network
) {
    private var splashServiceApi: SplashServiceApi = network.getRetrofit().create(SplashServiceApi::class.java)

    suspend fun getVersionSupportStatus(): ResultWrapper<BaseResponse<AppVersionStatus>> {
        return network.invokeApiCall(dispatcher) { splashServiceApi.getVersionSupportStatus() }
    }

    suspend fun getConfig(configVersion: String): ResultWrapper<BaseResponse<Config>> {
        return network.invokeApiCall(dispatcher) { splashServiceApi.getConfiguration(configVersion = configVersion) }
    }
}