package com.kavi.pbc.droid.app

import android.app.Application
import com.kavi.droid.color.palette.KvColorPalette
import com.kavi.pbc.droid.lib.common.ui.theme.BaseColor
import com.kavi.pbc.droid.lib.datastore.AppDatastore
import com.kavi.pbc.droid.network.Network
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class PBCLiveApp: Application() {

    @Inject
    lateinit var network: Network

    @Inject
    lateinit var appDatastore: AppDatastore

    override fun onCreate() {
        super.onCreate()

        // Initiate kv-color-palette for application theming
        KvColorPalette.initialize(baseColor = BaseColor)

        // Initiate network library
        network.init(NetworkConfig.networkConfig)

        // Initiate the datastore
        appDatastore.init(baseContext)
    }
}