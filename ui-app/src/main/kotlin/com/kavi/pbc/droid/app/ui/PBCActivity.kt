package com.kavi.pbc.droid.app.ui

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kavi.pbc.droid.app.ContractConfig
import com.kavi.pbc.droid.data.dto.DeviceFactor
import com.kavi.pbc.droid.lib.common.ui.theme.PBCAppTheme
import com.kavi.pbc.droid.lib.parent.contract.ContractName.SPLASH_CONTRACT
import com.kavi.pbc.droid.lib.parent.contract.ContractRegistry
import com.kavi.pbc.droid.lib.parent.extension.getDeviceFormFactor
import com.kavi.pbc.droid.lib.parent.contract.module.SplashContract
import com.kavi.pbc.droid.network.session.Session
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PBCActivity: ComponentActivity() {
    
    @Inject
    lateinit var contractRegistry: ContractRegistry

    @Inject
    lateinit var contractConfig: ContractConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Register UI contracts
        contractConfig.registerContracts()

        // Evaluate device form factor
        evaluateDeviceFormFactor()

        // Write the app version
        evaluateAppVersion()

        setContent {
            PBCAppTheme {
                contractRegistry.getContract<SplashContract>(SPLASH_CONTRACT).RetrieveNavGraph()
            }
        }
    }

    private fun evaluateDeviceFormFactor() {
        val deviceFactor = getDeviceFormFactor(this)
        Session.deviceFactor = DeviceFactor.valueOf(deviceFactor)
    }

    private fun evaluateAppVersion() {
        val appVersion = try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            packageInfo.versionName?: "0"
        } catch (ex: PackageManager.NameNotFoundException) {
            "0"
        }
        Session.appVersion = appVersion
    }
}