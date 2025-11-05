package com.kavi.pbc.droid.temple.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kavi.pbc.droid.temple.ui.TempleAboutUs
import com.kavi.pbc.droid.temple.ui.TempleContactUs
import javax.inject.Inject

class TempleNavigation @Inject constructor() {

    @Inject
    lateinit var templeAboutUs: TempleAboutUs

    @Inject
    lateinit var templeContactUs: TempleContactUs

    @Composable
    fun TempleNavGraph(startDestination: String = "temple/about-us") {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = startDestination,
            enterTransition = { fadeIn(animationSpec = tween(durationMillis = 500)) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 500)) }
        ) {
            composable (route = "temple/about-us") {
                templeAboutUs.AboutUsUI()
            }
            composable (route = "temple/contact-us") {
                templeContactUs.ContactUsUI()
            }
        }
    }
}