package com.kavi.pbc.droid.data.dto.user

enum class UserAuthType(val authType: Int) {
    GOOGLE(101),
    APPLE(102),
    NONE(-100)
}