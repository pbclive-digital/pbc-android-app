package com.kavi.pbc.droid.user.data.repository

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.user.User
import com.kavi.pbc.droid.user.data.model.UserRoleUpdateReq
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserServiceApi {

    @GET("/user/get/email/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): BaseResponse<User>

    @GET("/user/get/name/{name}")
    suspend fun getUserByName(@Path("name") name: String): BaseResponse<MutableList<User>>

    @PUT("/user/update/userType")
    suspend fun modifyUserType(@Body userRoleUpdateReq: UserRoleUpdateReq): BaseResponse<User>
}