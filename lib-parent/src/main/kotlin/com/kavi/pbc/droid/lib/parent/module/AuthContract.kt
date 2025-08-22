package com.kavi.pbc.droid.lib.parent.module

import com.kavi.pbc.droid.lib.parent.CommonContract

interface AuthContract: CommonContract {
    fun signOut()

    fun signInWithLastSignInAcc(onSignedIn: () -> Unit, onNoSignIn: () -> Unit)
}