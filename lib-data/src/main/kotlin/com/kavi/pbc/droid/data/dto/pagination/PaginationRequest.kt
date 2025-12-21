package com.kavi.pbc.droid.data.dto.pagination

import kotlinx.serialization.Serializable

@Serializable
data class PaginationRequest(
    val previousPageLastDocKey: String?
)
