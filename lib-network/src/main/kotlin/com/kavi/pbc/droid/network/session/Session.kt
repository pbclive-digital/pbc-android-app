package com.kavi.pbc.droid.network.session

import com.kavi.pbc.droid.data.dto.Config
import com.kavi.pbc.droid.data.dto.DeviceFactor
import com.kavi.pbc.droid.data.dto.auth.AuthToken
import com.kavi.pbc.droid.data.dto.user.User

object Session {
    var authToken: AuthToken? = null
    var user: User? = null
    var appVersion: String? = null
    var deviceFactor: DeviceFactor? = DeviceFactor.PHONE

    fun isLogIn(): Boolean {
        return authToken != null && user != null
    }
}