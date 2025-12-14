package com.kavi.pbc.droid.auth.data.repository.local

import com.kavi.pbc.droid.lib.datastore.AppDatastore
import com.kavi.pbc.droid.lib.datastore.DataKey
import javax.inject.Inject

class AuthLocalRepository @Inject constructor(
    private val appDatastore: AppDatastore
) {
    suspend fun updatePushNotificationUpdateStatus() {
        appDatastore.storeValue(DataKey.APP_IS_NEED_TO_UPDATE_PUSH_TOKEN, true)
    }
}