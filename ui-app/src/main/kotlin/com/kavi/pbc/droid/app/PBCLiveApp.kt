package com.kavi.pbc.droid.app

import android.app.Application
import androidx.compose.ui.graphics.Color
import com.kavi.droid.color.palette.KvColorPalette
import com.kavi.pbc.droid.lib.common.ui.theme.BaseColor
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PBCLiveApp: Application() {

    override fun onCreate() {
        super.onCreate()
        KvColorPalette.initialize(baseColor = BaseColor)
    }
}