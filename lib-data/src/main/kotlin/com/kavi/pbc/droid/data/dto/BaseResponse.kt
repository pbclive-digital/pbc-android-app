package com.kavi.pbc.droid.data.dto

enum class ResponseStatus {
    SUCCESS, ERROR, PENDING
}

data class BaseResponse<T>(
    val status: ResponseStatus,
    val body: T? = null,
    val errors: List<Error>? = null
)
