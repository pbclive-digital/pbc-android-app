package com.kavi.pbc.droid.app.di

import com.kavi.pbc.droid.lib.parent.module.SplashContract
import com.kavi.pbc.droid.splash.SplashContractImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UIModuleBinding {
    @Binds
    abstract fun callSplashContract(splashContractImpl: SplashContractImpl): SplashContract
}