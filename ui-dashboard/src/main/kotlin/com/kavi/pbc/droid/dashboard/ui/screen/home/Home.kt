package com.kavi.pbc.droid.dashboard.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kavi.pbc.droid.dashboard.R
import com.kavi.pbc.droid.lib.common.ui.component.TitleWithAction
import com.kavi.pbc.droid.lib.common.ui.component.TitleWithProfile
import com.kavi.pbc.droid.lib.parent.contract.ContractName.AUTH_CONTRACT
import com.kavi.pbc.droid.lib.parent.contract.ContractRegistry
import com.kavi.pbc.droid.lib.parent.contract.module.AuthContract
import com.kavi.pbc.droid.network.session.Session
import javax.inject.Inject

class Home @Inject constructor() {

    @Inject
    lateinit var contractRegistry: ContractRegistry

    @Composable
    fun HomeUI(navController: NavHostController, modifier: Modifier = Modifier) {

        Box (
            modifier = Modifier
                .padding(top = 56.dp, start = 16.dp, end = 16.dp)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Session.user?.profilePicUrl?.let {
                    TitleWithProfile(
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                        titleText = stringResource(R.string.label_pbc),
                        profilePicUrl = it,
                        profileAction = {
                            contractRegistry.getContract<AuthContract>(AUTH_CONTRACT).signOut()
                            navController.navigate("dashboard/to/auth") {
                                // Remove SplashUI from backstack
                                popUpTo("dashboard/dashboard-ui") {
                                    inclusive = true
                                }
                            }

                        }
                    )
                }?: run {
                    TitleWithAction(
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                        titleText = stringResource(R.string.label_pbc),
                        icon = painterResource(com.kavi.pbc.droid.lib.common.ui.R.drawable.image_dhamma_chakra),
                        iconAction = {}
                    )
                }
            }
        }
    }
}