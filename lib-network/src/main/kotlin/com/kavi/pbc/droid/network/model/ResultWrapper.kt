package com.kavi.pbc.droid.network.model

import com.kavi.pbc.droid.network.dto.Error
import com.kavi.pbc.droid.network.dto.BaseResponse

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: BaseResponse<@UnsafeVariance T>): ResultWrapper<BaseResponse<@UnsafeVariance T>>()
    data class UnAuthError(val code: Int? = null, val error: Error? = null): ResultWrapper<Nothing>()
    data class HttpError(val code: Int? = null, val error: Error? = null): ResultWrapper<Nothing>()
    object NetworkError: ResultWrapper<Nothing>()
}