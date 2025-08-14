package com.kavi.pbc.droid.splash.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.network.model.ResultWrapper
import com.kavi.pbc.droid.splash.data.repository.SplashRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val remoteDataSource: SplashRemoteRepository
): ViewModel() {

    fun fetchVersionSupportStatus() {
        viewModelScope.launch {
            when(val response = remoteDataSource.getVersionSupportStatus()) {
                is ResultWrapper.NetworkError -> {
                    Log.d("NETWORK RESULT", "NetworkError")
                }
                is ResultWrapper.HttpError -> {
                    Log.d("NETWORK RESULT", "HttpError")
                }
                is ResultWrapper.UnAuthError -> {
                    Log.d("NETWORK RESULT", "UnAuthError")
                }
                is ResultWrapper.Success -> {
                    response.value.body?.let {
                        Log.d("NETWORK RESULT", it.toString())
                    }
                }
            }
        }
    }
}