package com.kavi.pbc.droid.auth.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.auth.data.repository.local.AuthLocalRepository
import com.kavi.pbc.droid.auth.data.repository.remote.AuthRemoteRepository
import com.kavi.pbc.droid.auth.util.AuthUtil
import com.kavi.pbc.droid.data.dto.auth.AuthToken
import com.kavi.pbc.droid.data.dto.auth.TokenStatus
import com.kavi.pbc.droid.data.dto.user.User
import com.kavi.pbc.droid.network.model.ResultWrapper
import com.kavi.pbc.droid.network.session.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val remoteDataSource: AuthRemoteRepository,
    private val localDataSource: AuthLocalRepository
): ViewModel() {

    private val _onUnRegistered = MutableStateFlow(false)
    val onUnRegistered: StateFlow<Boolean> = _onUnRegistered

    private val _onSignedIn = MutableStateFlow(false)
    val onSignedIn: StateFlow<Boolean> = _onSignedIn

    private val _signedUser = MutableStateFlow(User(email = ""))
    val signedUser: StateFlow<User> = _signedUser

    fun fetchUserStatus(email: String, userId: String) {
        viewModelScope.launch {
            when(val response = remoteDataSource.getUserStatus(email = email, userId = userId)) {
                is ResultWrapper.NetworkError -> {
                    Log.e("NETWORK RESULT", "NetworkError")
                }
                is ResultWrapper.HttpError -> {
                    Log.d("NETWORK RESULT", "HttpError")
                }
                is ResultWrapper.UnAuthError -> {

                }
                is ResultWrapper.Success -> {
                    response.value.body?.let { status ->
                       when(status) {
                           "REGISTERED" -> requestAuthToken(email = email, userId = userId)
                           "UNREGISTERED" -> {
                               _signedUser.value = AuthUtil.createUserFromFirebaseAuth(email = email)
                               _onUnRegistered.value = true
                           }
                       }
                    }
                }
            }
        }
    }

    fun requestAuthToken(email: String, userId: String) {
        viewModelScope.launch {
            when(val response = remoteDataSource.requestAuthToken(email = email, userId = userId)) {
                is ResultWrapper.NetworkError -> {
                    Log.e("NETWORK RESULT", "NetworkError")
                }
                is ResultWrapper.HttpError -> {
                    Log.d("NETWORK RESULT", "HttpError")
                }
                is ResultWrapper.UnAuthError -> {
                    generateAuthToken(userId = userId)
                }
                is ResultWrapper.Success -> {
                    response.value.body?.let { authToken ->
                        Session.authToken = authToken
                        fetchUser(userId = userId)
                    }
                }
            }
        }
    }

    fun generateAuthToken(userId: String) {
        var authToken = AuthToken(
            email = _signedUser.value.email,
            userId = _signedUser.value.id,
            status = TokenStatus.VALID,
        )

        viewModelScope.launch {
            when(val response = remoteDataSource.createNewToken(authToken)) {
                is ResultWrapper.NetworkError -> {
                    Session.authToken = null
                }
                is ResultWrapper.HttpError -> {
                    Session.authToken = null
                }
                is ResultWrapper.UnAuthError -> {}
                is ResultWrapper.Success -> {
                    authToken = response.value.body!!
                    Session.authToken = authToken
                    fetchUser(userId = userId)
                }
            }
        }
    }

    fun fetchUser(userId: String) {
        viewModelScope.launch {
            when(val response = remoteDataSource.getUser(userId = userId)) {
                is ResultWrapper.NetworkError -> {
                    Session.user = null
                }
                is ResultWrapper.HttpError -> {
                    Session.user = null
                }
                is ResultWrapper.UnAuthError -> {}
                is ResultWrapper.Success -> {
                    response.value.body?.let { user ->
                        Session.user = user
                        _onSignedIn.value = true

                        // Update push notification sync flag to true after new login
                        localDataSource.updatePushNotificationUpdateStatus()
                    }
                }
            }
        }
    }

    fun resetRegistrationUINavigation() {
        _onUnRegistered.value = false
    }

    fun resetDashboardUINavigation() {
        _onSignedIn.value = false
    }
}