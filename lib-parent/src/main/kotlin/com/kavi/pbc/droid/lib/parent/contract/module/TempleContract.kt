package com.kavi.pbc.droid.lib.parent.contract.module

import androidx.compose.runtime.Composable
import com.kavi.pbc.droid.lib.parent.contract.CommonContract

interface TempleContract: CommonContract {
    @Composable
    fun RetrieveNavGraphWithDynamicDestination(startDestination: String)
}