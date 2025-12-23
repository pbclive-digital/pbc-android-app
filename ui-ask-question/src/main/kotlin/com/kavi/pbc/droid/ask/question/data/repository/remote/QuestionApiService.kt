package com.kavi.pbc.droid.ask.question.data.repository.remote

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.pagination.PaginationRequest
import com.kavi.pbc.droid.data.dto.pagination.PaginationResponse
import com.kavi.pbc.droid.data.dto.question.AnswerComment
import com.kavi.pbc.droid.data.dto.question.Question
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface QuestionApiService {

    @GET("question/get/user/{userId}")
    suspend fun getUserQuestionList(@Path("userId") userId: String): BaseResponse<MutableList<Question>>

    @POST("question/get/all")
    suspend fun getAllQuestionList(@Body paginationReq: PaginationRequest? = null): BaseResponse<PaginationResponse<Question>>

    @POST("question/create")
    suspend fun createNewQuestion(@Body newQuestion: Question): BaseResponse<String>

    @PUT("question/update/{questionId}")
    suspend fun modifyQuestion(@Path("questionId") questionId: String,
                               @Body newQuestion: Question): BaseResponse<Question>

    @DELETE("question/delete/{questionId}")
    suspend fun deleteQuestion(@Path("questionId") questionId: String): BaseResponse<String>

    @PUT("question/add/comment/{questionId}")
    suspend fun createNewAnswer(@Path("questionId") questionId: String,
                                @Body answerComment: AnswerComment): BaseResponse<Question>
}