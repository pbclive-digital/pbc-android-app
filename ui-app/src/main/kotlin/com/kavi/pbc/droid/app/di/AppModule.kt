package com.kavi.pbc.droid.app.di

import com.kavi.pbc.droid.lib.datastore.AppDatastore
import com.kavi.pbc.droid.lib.datastore.AppInMemoryStore
import com.kavi.pbc.droid.network.Network
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideNetworkInstance(): Network {
        return Network()
    }

    @Provides
    @Singleton
    fun provideAppInMemoryStore(): AppInMemoryStore {
        return AppInMemoryStore()
    }

    @Provides
    @Singleton
    fun provideAppDatastore(): AppDatastore {
        return AppDatastore()
    }
}