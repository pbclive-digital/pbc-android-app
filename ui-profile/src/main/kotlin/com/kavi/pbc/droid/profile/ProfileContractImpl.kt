package com.kavi.pbc.droid.profile

import androidx.compose.runtime.Composable
import com.kavi.pbc.droid.lib.parent.contract.module.ProfileContract
import com.kavi.pbc.droid.profile.navigation.ProfileNavigation
import javax.inject.Inject

class ProfileContractImpl @Inject constructor(): ProfileContract {

    @Inject
    lateinit var profileNavigation: ProfileNavigation

    @Composable
    override fun RetrieveNavGraph() {
        profileNavigation.ProfileNavGraph()
    }
}