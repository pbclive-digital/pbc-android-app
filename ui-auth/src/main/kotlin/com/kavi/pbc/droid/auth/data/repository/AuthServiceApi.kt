package com.kavi.pbc.droid.auth.data.repository

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.auth.AuthToken
import com.kavi.pbc.droid.data.dto.user.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthServiceApi {

    @GET("/auth/get/{email}/{userId}")
    suspend fun getUserStatus(@Path("email") email: String, @Path("userId") userId: String): BaseResponse<String>

    @GET("/user/get/{id}")
    suspend fun getUser(@Path("id") userId: String): BaseResponse<User>
    
    @GET("/auth/get/token/{email}/{userId}")
    suspend fun requestAuthToken(@Path("email") email: String, @Path("userId") userId: String): BaseResponse<AuthToken>

    @POST("/user/create")
    suspend fun registerUser(@Body user: User): BaseResponse<String>

    @POST("/auth/create/token")
    suspend fun createNewToken(@Body token: AuthToken): BaseResponse<AuthToken>
}