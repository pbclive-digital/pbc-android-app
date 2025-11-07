package com.kavi.pbc.droid.data.dto

import com.kavi.pbc.droid.data.dto.user.User
import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val dashboardEventCount: Int = 2,
    val dailyQuotesCount: Int = 3,
    val residentMonkList: List<User> = emptyList()
)
