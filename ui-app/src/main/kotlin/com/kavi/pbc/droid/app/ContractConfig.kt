package com.kavi.pbc.droid.app

import com.kavi.pbc.droid.auth.AuthContractImpl
import com.kavi.pbc.droid.dashboard.DashboardContractImpl
import com.kavi.pbc.droid.event.EventContractImpl
import com.kavi.pbc.droid.lib.parent.contract.ContractName
import com.kavi.pbc.droid.lib.parent.contract.ContractRegistry
import com.kavi.pbc.droid.profile.ProfileContractImpl
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
    @Inject
    lateinit var profileContractImpl: ProfileContractImpl

    fun registerContracts() {
        contractRegistry.registerContract(ContractName.SPLASH_CONTRACT, splashContractImpl)
        contractRegistry.registerContract(ContractName.AUTH_CONTRACT, authContractImpl)
        contractRegistry.registerContract(ContractName.DASHBOARD_CONTRACT, dashboardContractImpl)
        contractRegistry.registerContract(ContractName.EVENT_CONTRACT, eventContractImpl)
        contractRegistry.registerContract(ContractName.PROFILE_CONTRACT, profileContractImpl)
    }
}