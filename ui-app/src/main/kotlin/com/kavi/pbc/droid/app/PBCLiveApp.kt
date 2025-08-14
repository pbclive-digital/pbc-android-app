package com.kavi.pbc.droid.app

import android.app.Application
import androidx.compose.ui.graphics.Color
import com.kavi.droid.color.palette.KvColorPalette
import com.kavi.pbc.droid.lib.common.ui.theme.BaseColor
import com.kavi.pbc.droid.network.Network
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class PBCLiveApp: Application() {

    @Inject
    lateinit var network: Network

    override fun onCreate() {
        super.onCreate()
        KvColorPalette.initialize(baseColor = BaseColor)

        network.init(NetworkConfig.networkConfig)
    }
}