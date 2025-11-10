package com.kavi.pbc.droid.lib.parent.contract.module

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.kavi.pbc.droid.lib.parent.contract.CommonContract

interface AuthContract: CommonContract {
    fun signOut()

    fun signInWithLastSignInAcc(onSignedIn: () -> Unit, onNoSignIn: () -> Unit)

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AuthInviteBottomSheet(sheetState: SheetState, showSheet: MutableState<Boolean>, onRegister: () -> Unit)
}