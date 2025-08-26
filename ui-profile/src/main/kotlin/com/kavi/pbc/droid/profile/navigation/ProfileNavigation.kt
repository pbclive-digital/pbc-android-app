package com.kavi.pbc.droid.profile.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kavi.pbc.droid.lib.parent.contract.ContractName.AUTH_CONTRACT
import com.kavi.pbc.droid.lib.parent.contract.ContractRegistry
import com.kavi.pbc.droid.lib.parent.contract.module.AuthContract
import com.kavi.pbc.droid.profile.ui.Profile
import javax.inject.Inject

class ProfileNavigation @Inject constructor() {

    @Inject
    lateinit var profile: Profile

    @Inject
    lateinit var contractRegistry: ContractRegistry

    @Composable
    fun ProfileNavGraph() {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = "profile/profile-ui",
            enterTransition = { fadeIn(animationSpec = tween(durationMillis = 500)) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 500)) }
        ) {
            composable (route = "profile/profile-ui") {
                profile.ProfileUI(navController = navController)
            }
            composable (route = "profile/to/auth") {
                contractRegistry.getContract<AuthContract>(AUTH_CONTRACT).RetrieveNavGraph()
            }
        }
    }
}