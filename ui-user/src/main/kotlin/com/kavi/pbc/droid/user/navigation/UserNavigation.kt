package com.kavi.pbc.droid.user.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kavi.pbc.droid.user.ui.manage.UserManage
import javax.inject.Inject

class UserNavigation @Inject constructor() {

    @Inject
    lateinit var userManage: UserManage

    @Composable
    fun UserNavGraph(startDestination: String = "user/user-manage") {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = startDestination,
            enterTransition = { fadeIn(animationSpec = tween(durationMillis = 500)) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 500)) }
        ) {
            composable (route = "user/user-manage") {
                userManage.UserManageUI(navController = navController)
            }
        }
    }
}