package com.kavi.pbc.droid.lib.parent.contract.module

import com.kavi.pbc.droid.lib.parent.contract.CommonContract

interface AuthContract: CommonContract {
    fun signOut()

    fun signInWithLastSignInAcc(onSignedIn: () -> Unit, onNoSignIn: () -> Unit)
}