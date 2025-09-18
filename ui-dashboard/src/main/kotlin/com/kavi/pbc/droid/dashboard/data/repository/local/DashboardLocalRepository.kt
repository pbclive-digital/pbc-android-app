package com.kavi.pbc.droid.dashboard.data.repository.local

import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.data.dto.quote.DailyQuote
import com.kavi.pbc.droid.lib.datastore.AppDatastore
import com.kavi.pbc.droid.lib.datastore.AppInMemoryStore
import com.kavi.pbc.droid.lib.datastore.DataKey
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class DashboardLocalRepository @Inject constructor(
    private val inMemoryStore: AppInMemoryStore,
    private val appDatastore: AppDatastore
) {
    fun setSelectedEvent(event: Event): String {
        val randomEventKey = UUID.randomUUID().toString()
        inMemoryStore.storeValue(randomEventKey, event)
        return randomEventKey
    }

    suspend fun storeDailyQuoteToDatastore(dailyQuote: DailyQuote) {
        appDatastore.storeObject<DailyQuote>(DataKey.APP_DAILY_QUOTE, dailyQuote)
    }

    suspend fun retrieveDailyQuoteFromDatastore(): Flow<DailyQuote?> {
        return appDatastore.retrieveObject<DailyQuote>(key = DataKey.APP_DAILY_QUOTE)
    }
}