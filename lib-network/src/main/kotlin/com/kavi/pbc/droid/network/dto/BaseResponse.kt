package com.kavi.pbc.droid.network.dto

enum class ResponseStatus {
    SUCCESS, ERROR, PENDING
}

data class BaseResponse<T>(
    val status: ResponseStatus,
    val body: T? = null,
    val errors: List<Error>? = null
)
