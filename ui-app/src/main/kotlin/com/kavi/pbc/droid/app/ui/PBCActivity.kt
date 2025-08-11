package com.kavi.pbc.droid.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kavi.pbc.droid.lib.common.ui.theme.PBCAppTheme
import com.kavi.pbc.droid.lib.parent.module.SplashContract
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PBCActivity: ComponentActivity() {
    @Inject
    lateinit var splashContract: SplashContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PBCAppTheme {
                splashContract.GetNavGraph()
            }
        }
    }
}