package com.kavi.pbc.droid.splash.data.repository

import com.kavi.pbc.droid.network.dto.AppVersionStatus
import com.kavi.pbc.droid.network.dto.BaseResponse
import com.kavi.pbc.droid.network.dto.Config
import retrofit2.http.GET

interface SplashServiceApi {

    @GET("/config/get/app-support/status")
    suspend fun getVersionSupportStatus(): BaseResponse<AppVersionStatus>

    @GET("/config/get/v1")
    suspend fun getConfiguration(): BaseResponse<Config>
}