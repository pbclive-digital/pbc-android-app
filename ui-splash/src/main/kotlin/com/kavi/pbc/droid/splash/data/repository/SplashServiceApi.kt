package com.kavi.pbc.droid.splash.data.repository

import com.kavi.pbc.droid.data.dto.AppVersionStatus
import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.Config
import retrofit2.http.GET
import retrofit2.http.Path

interface SplashServiceApi {

    @GET("/config/get/app-support/status")
    suspend fun getVersionSupportStatus(): BaseResponse<AppVersionStatus>

    @GET("/config/get/{configVersion}")
    suspend fun getConfiguration(@Path("configVersion") configVersion: String): BaseResponse<Config>
}