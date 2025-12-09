package com.kavi.pbc.droid.profile.data.repository.remote

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.user.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProfileServiceApi {

    @DELETE("/user/delete/{userId}")
    suspend fun deleteUserAccount(@Path("userId") userId: String): BaseResponse<String>

    @PUT("/user/update/{userId}")
    suspend fun updateUserAccount(@Path("userId") userId: String, @Body user: User): BaseResponse<User>
}