package com.kavi.pbc.droid.auth

import androidx.compose.runtime.Composable
import com.kavi.pbc.droid.auth.navigation.AuthNavigation
import com.kavi.pbc.droid.lib.parent.module.AuthContract
import javax.inject.Inject

class AuthContractImpl @Inject constructor(): AuthContract {

    @Inject
    lateinit var authNavigation: AuthNavigation

    @Composable
    override fun RetrieveNavGraph() {
        authNavigation.AuthNavGraph()
    }
}