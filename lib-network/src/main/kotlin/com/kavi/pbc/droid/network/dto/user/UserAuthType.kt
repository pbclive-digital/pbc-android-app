package com.kavi.pbc.droid.network.dto.user

enum class UserAuthType(val authType: Int) {
    GOOGLE(101),
    APPLE(102),
    NONE(-100)
}