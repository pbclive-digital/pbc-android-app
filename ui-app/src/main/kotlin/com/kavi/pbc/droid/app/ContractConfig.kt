package com.kavi.pbc.droid.app

import com.kavi.pbc.droid.auth.AuthContractImpl
import com.kavi.pbc.droid.dashboard.DashboardContractImpl
import com.kavi.pbc.droid.event.EventContractImpl
import com.kavi.pbc.droid.lib.parent.ContractRegistry
import com.kavi.pbc.droid.splash.SplashContractImpl
import javax.inject.Inject

class ContractConfig @Inject constructor() {

    @Inject
    lateinit var contractRegistry: ContractRegistry

    @Inject
    lateinit var splashContractImpl: SplashContractImpl
    @Inject
    lateinit var authContractImpl: AuthContractImpl
    @Inject
    lateinit var dashboardContractImpl: DashboardContractImpl
    @Inject
    lateinit var eventContractImpl: EventContractImpl

    fun registerContracts() {
        contractRegistry.registerContract("splash", splashContractImpl)
        contractRegistry.registerContract("auth", authContractImpl)
        contractRegistry.registerContract("dashboard", dashboardContractImpl)
        contractRegistry.registerContract("event", eventContractImpl)
    }
}