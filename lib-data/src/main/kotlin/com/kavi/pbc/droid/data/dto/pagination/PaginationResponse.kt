package com.kavi.pbc.droid.data.dto.pagination

import kotlinx.serialization.Serializable

@Serializable
data class PaginationResponse<T>(
    val entityList: MutableList<T>,
    val previousPageLastDocKey: String?
)
