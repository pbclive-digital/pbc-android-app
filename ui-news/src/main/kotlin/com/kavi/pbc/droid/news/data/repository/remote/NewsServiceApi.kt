package com.kavi.pbc.droid.news.data.repository.remote

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.news.News
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NewsServiceApi {

    @GET("/news/get/draft")
    suspend fun fetchDraftNewsList(): BaseResponse<MutableList<News>>

    @GET("/news/get/active")
    suspend fun fetchActiveNewsList(): BaseResponse<MutableList<News>>

    @POST("/news/create")
    suspend fun createNews(@Body news: News): BaseResponse<String>

    @PUT("/news/update/{newsId}")
    suspend fun updateNews(@Path("newsId") newsId: String, @Body news: News): BaseResponse<News>

    @DELETE("/news/delete/{newsId}")
    suspend fun deleteNews(@Path("newsId") newsId: String): BaseResponse<String>
}