package com.kavi.pbc.droid.splash.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.lib.datastore.AppInMemoryStore
import com.kavi.pbc.droid.lib.datastore.DataKey
import com.kavi.pbc.droid.lib.parent.contract.ContractName.AUTH_CONTRACT
import com.kavi.pbc.droid.lib.parent.contract.ContractRegistry
import com.kavi.pbc.droid.lib.parent.contract.module.AuthContract
import com.kavi.pbc.droid.network.model.ResultWrapper
import com.kavi.pbc.droid.network.session.Session
import com.kavi.pbc.droid.splash.data.repository.SplashRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val remoteDataSource: SplashRemoteRepository
): ViewModel() {

    @Inject
    lateinit var contractRegistry: ContractRegistry

    @Inject
    lateinit var appInMemoryStore: AppInMemoryStore

    private val _isNoSupport = MutableStateFlow(false)
    val isNoSupport: StateFlow<Boolean> = _isNoSupport
    private val _isNoConnection = MutableStateFlow(false)
    val isNoConnection: StateFlow<Boolean> = _isNoConnection
    private val _navigateToAuth = MutableStateFlow(false)
    val navigateToAuth: StateFlow<Boolean> = _navigateToAuth
    private val _navigateToDashboard = MutableStateFlow(false)
    val navigateToDashboard: StateFlow<Boolean> = _navigateToDashboard

    fun fetchVersionSupportStatus() {
        viewModelScope.launch {
            when(val response = remoteDataSource.getVersionSupportStatus()) {
                is ResultWrapper.NetworkError -> {
                    Log.e("NETWORK RESULT", "NetworkError")
                    _isNoConnection.value = true
                }
                is ResultWrapper.HttpError -> {
                    Log.d("NETWORK RESULT", "HttpError")
                }
                is ResultWrapper.UnAuthError -> {}
                is ResultWrapper.Success -> {
                    response.value.body?.let {
                        if (it.support) {
                            fetchConfig()
                        } else {
                            _isNoSupport.value = true
                        }
                    }
                }
            }
        }
    }

    fun fetchConfig() {
        val authContract = contractRegistry.getContract<AuthContract>(AUTH_CONTRACT)

        viewModelScope.launch {
            when(val response = remoteDataSource.getConfig(configVersion = "v1")) {
                is ResultWrapper.NetworkError -> {
                    Log.e("NETWORK RESULT", "NetworkError")
                    _isNoConnection.value = true
                }
                is ResultWrapper.HttpError -> {
                    Log.d("NETWORK RESULT", "HttpError")
                }
                is ResultWrapper.UnAuthError -> {}
                is ResultWrapper.Success -> {
                    response.value.body?.let { config ->
                        appInMemoryStore.storeValue(DataKey.APP_CONFIG, config)
                        authContract.signInWithLastSignInAcc(onSignedIn = {
                            _navigateToDashboard.value = true
                        }, onNoSignIn = {
                            _navigateToAuth.value = true
                        })
                    }
                }
            }
        }
    }
}