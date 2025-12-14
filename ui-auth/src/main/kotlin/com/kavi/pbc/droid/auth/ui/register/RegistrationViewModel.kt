package com.kavi.pbc.droid.auth.ui.register

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
class RegistrationViewModel @Inject constructor(
    private val remoteDataSource: AuthRemoteRepository,
    private val localDataSource: AuthLocalRepository
): ViewModel() {

    private val _signedUser = MutableStateFlow(User(email = ""))
    val signedUsed: StateFlow<User> = _signedUser

    private val _onUserCreated = MutableStateFlow(false)
    val onUserCreated: StateFlow<Boolean> = _onUserCreated

    fun createUserFromFirebaseAuth(email: String) {
        _signedUser.value = AuthUtil.createUserFromFirebaseAuth(email = email)
    }

    fun registerNewUser(
        firstName: String?, lastName: String?, phoneNum: String?, address: String?
    ) {
        _signedUser.value.apply {
            this.firstName = firstName
            this.lastName = lastName
            this.phoneNumber = phoneNum
            this.address = address
        }

        viewModelScope.launch {
            when(remoteDataSource.registerUser(user = _signedUser.value)) {
                is ResultWrapper.NetworkError -> {
                    Log.e("NETWORK RESULT", "NetworkError")
                }
                is ResultWrapper.HttpError -> {
                    Log.d("NETWORK RESULT", "HttpError")
                }
                is ResultWrapper.UnAuthError -> {}
                is ResultWrapper.Success -> {
                    Session.user = _signedUser.value
                    generateAuthToken()
                }
            }
        }
    }

    fun generateAuthToken() {
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
                    // Navigate to Dashboard
                    _onUserCreated.value = true

                    // Update push notification sync flag to true after new login
                    localDataSource.updatePushNotificationUpdateStatus()
                }
            }
        }
    }

    fun resetOnUserCreated() {
        _onUserCreated.value = false
    }
}