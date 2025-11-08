package com.kavi.pbc.droid.user

import androidx.compose.runtime.Composable
import com.kavi.pbc.droid.lib.parent.contract.module.UserContract
import com.kavi.pbc.droid.user.navigation.UserNavigation
import javax.inject.Inject

class UserContractImpl @Inject constructor(): UserContract {
    @Inject
    lateinit var userNavigation: UserNavigation

    @Composable
    override fun RetrieveNavGraph() {
        userNavigation.UserNavGraph()
    }
}