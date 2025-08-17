package com.kavi.pbc.droid.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String? = null,
    val email: String,
    var firstName: String? = null,
    var lastName: String? = null,
    var phoneNumber: String? = null,
    val profilePicUrl: String? = null,
    val userType: UserType = UserType.CONSUMER,
    val userAuthType: UserAuthType? = UserAuthType.NONE,
)
