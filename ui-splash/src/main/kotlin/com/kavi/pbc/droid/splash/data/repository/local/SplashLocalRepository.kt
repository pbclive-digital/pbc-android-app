package com.kavi.pbc.droid.splash.data.repository.local

import com.kavi.pbc.droid.data.dto.Config
import com.kavi.pbc.droid.lib.datastore.AppInMemoryStore
import com.kavi.pbc.droid.lib.datastore.DataKey
import javax.inject.Inject

class SplashLocalRepository @Inject constructor(
    private val inMemoryStore: AppInMemoryStore
) {
    fun storeAppConfig(config: Config) {
        inMemoryStore.storeValue(DataKey.APP_CONFIG, config)
    }
}