package com.kavi.pbc.droid.data.dto.quote

import kotlinx.serialization.Serializable

@Serializable
data class Quote(
    val quote: String,
    val author: String
)