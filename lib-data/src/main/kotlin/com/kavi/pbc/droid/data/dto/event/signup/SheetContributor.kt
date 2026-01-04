package com.kavi.pbc.droid.data.dto.event.signup

import kotlinx.serialization.Serializable

@Serializable
data class SheetContributor(
    val contributorId: String,
    val contributorName: String,
    val contributorContactNumber: String?
)
