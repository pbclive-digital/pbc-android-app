package com.kavi.pbc.droid.auth.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kavi.pbc.droid.auth.ui.AuthUI
import com.kavi.pbc.droid.auth.ui.RegisterUI
import javax.inject.Inject

class AuthNavigation @Inject constructor() {
    @Composable
    fun AuthNavGraph() {
        NavHost(navController = rememberNavController(), startDestination = "auth/auth-ui") {
            composable (route = "auth/auth-ui") {
                AuthUI()
            }
            composable (route = "auth/registration-ui") {
                RegisterUI()
            }
        }
    }
}