package com.kavi.pbc.droid.app

import com.kavi.pbc.droid.appointment.AppointmentContractImpl
import com.kavi.pbc.droid.auth.AuthContractImpl
import com.kavi.pbc.droid.dashboard.DashboardContractImpl
import com.kavi.pbc.droid.event.EventContractImpl
import com.kavi.pbc.droid.lib.parent.contract.ContractServiceLocator
import com.kavi.pbc.droid.lib.parent.contract.module.AppointmentContract
import com.kavi.pbc.droid.lib.parent.contract.module.AuthContract
import com.kavi.pbc.droid.lib.parent.contract.module.DashboardContract
import com.kavi.pbc.droid.lib.parent.contract.module.EventContract
import com.kavi.pbc.droid.lib.parent.contract.module.ProfileContract
import com.kavi.pbc.droid.lib.parent.contract.module.SplashContract
import com.kavi.pbc.droid.lib.parent.contract.module.TempleContract
import com.kavi.pbc.droid.lib.parent.contract.module.UserContract
import com.kavi.pbc.droid.profile.ProfileContractImpl
import com.kavi.pbc.droid.splash.SplashContractImpl
import com.kavi.pbc.droid.temple.TempleContractImpl
import com.kavi.pbc.droid.user.UserContractImpl
import javax.inject.Inject

class ContractConfig @Inject constructor() {

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
    @Inject
    lateinit var templeContractImpl: TempleContractImpl
    @Inject
    lateinit var appointmentContractImpl: AppointmentContractImpl
    @Inject
    lateinit var userContractImpl: UserContractImpl

    fun registerContracts() {
        ContractServiceLocator.register(SplashContract::class) { splashContractImpl }
        ContractServiceLocator.register(AuthContract::class) { authContractImpl }
        ContractServiceLocator.register(DashboardContract::class) { dashboardContractImpl }
        ContractServiceLocator.register(EventContract::class) { eventContractImpl }
        ContractServiceLocator.register(ProfileContract::class) { profileContractImpl }
        ContractServiceLocator.register(TempleContract::class) { templeContractImpl }
        ContractServiceLocator.register(UserContract::class) { userContractImpl }
        ContractServiceLocator.register(AppointmentContract::class) { appointmentContractImpl }
    }
}