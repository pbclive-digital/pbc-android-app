package com.kavi.pbc.droid.profile.data.repository

import com.kavi.pbc.droid.data.dto.BaseResponse
import retrofit2.http.DELETE
import retrofit2.http.Path

interface ProfileServiceApi {

    @DELETE("/user/delete/{userId}")
    suspend fun deleteUserAccount(@Path("userId") userId: String): BaseResponse<String>
}