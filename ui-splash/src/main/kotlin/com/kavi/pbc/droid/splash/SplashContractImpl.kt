package com.kavi.pbc.droid.splash

import androidx.compose.runtime.Composable
import com.kavi.pbc.droid.lib.parent.contract.module.SplashContract
import com.kavi.pbc.droid.splash.navigation.SplashNavigation
import javax.inject.Inject

class SplashContractImpl @Inject constructor(): SplashContract {
    @Inject
    lateinit var splashNavigation: SplashNavigation

    @Composable
    override fun RetrieveNavGraph() {
        splashNavigation.SplashNavGraph()
    }
}