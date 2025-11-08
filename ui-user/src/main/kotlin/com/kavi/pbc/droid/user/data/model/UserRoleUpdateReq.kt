package com.kavi.pbc.droid.user.data.model

import com.kavi.pbc.droid.data.dto.user.User
import kotlinx.serialization.Serializable

@Serializable
data class UserRoleUpdateReq(
    val newRole: String = "",
    val residentMonkFlag: Boolean = false,
    val user: User
)
