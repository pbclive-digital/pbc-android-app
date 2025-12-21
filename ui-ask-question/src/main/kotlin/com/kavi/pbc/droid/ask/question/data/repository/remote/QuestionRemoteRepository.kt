package com.kavi.pbc.droid.ask.question.data.repository.remote

import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.pagination.PaginationRequest
import com.kavi.pbc.droid.data.dto.pagination.PaginationResponse
import com.kavi.pbc.droid.data.dto.question.Question
import com.kavi.pbc.droid.network.Network
import com.kavi.pbc.droid.network.model.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class QuestionRemoteRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val network: Network
) {
    private val questionServiceApi = network.getRetrofit().create(QuestionApiService::class.java)

    suspend fun getUserQuestionList(userId: String): ResultWrapper<BaseResponse<MutableList<Question>>> {
        return network.invokeApiCall(dispatcher) { questionServiceApi.getUserQuestionList(userId) }
    }

    suspend fun getAllQuestionList(paginationReq: PaginationRequest? = null): ResultWrapper<BaseResponse<PaginationResponse<Question>>> {
        return network.invokeApiCall(dispatcher) { questionServiceApi.getAllQuestionList(paginationReq) }
    }
}