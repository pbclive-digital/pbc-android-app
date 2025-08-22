package com.kavi.pbc.droid.auth

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.kavi.pbc.droid.auth.data.repository.AuthRemoteRepository
import com.kavi.pbc.droid.auth.navigation.AuthNavigation
import com.kavi.pbc.droid.auth.ui.auth.AuthViewModel
import com.kavi.pbc.droid.data.dto.auth.AuthToken
import com.kavi.pbc.droid.data.dto.auth.TokenStatus
import com.kavi.pbc.droid.lib.parent.module.AuthContract
import com.kavi.pbc.droid.network.model.ResultWrapper
import com.kavi.pbc.droid.network.session.Session
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthContractImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteRepository
): AuthContract {

    @Inject
    lateinit var authNavigation: AuthNavigation

    @Composable
    override fun RetrieveNavGraph() {
        authNavigation.AuthNavGraph()
    }

    override fun signOut() {
        Firebase.auth.signOut()
    }

    override fun signInWithLastSignInAcc(onSignedIn: () -> Unit, onNoSignIn: () -> Unit) {
        Firebase.auth.currentUser?.let { user ->
            println("Email: ${user.email}")
            println("ID: ${user.uid}")
            user.email?.let {
                requestAuthToken(email = it, userId = user.uid, onSignedIn = onSignedIn, onNoSignIn = onNoSignIn)
            }?: run {
                onNoSignIn.invoke()
            }
        }?: run {
            onNoSignIn.invoke()
        }
    }

    private fun requestAuthToken(email: String, userId: String, onSignedIn: () -> Unit, onNoSignIn: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            when(val response = remoteDataSource.requestAuthToken(email = email, userId = userId)) {
                is ResultWrapper.NetworkError -> {
                    onNoSignIn.invoke()
                }
                is ResultWrapper.HttpError -> {
                    onNoSignIn.invoke()
                }
                is ResultWrapper.UnAuthError -> {
                    val authToken = AuthToken(
                        email = email,
                        userId = userId,
                        status = TokenStatus.VALID,
                    )
                    createNewToken(userId = userId, authToken = authToken, onSignedIn = onSignedIn, onNoSignIn = onNoSignIn)
                }
                is ResultWrapper.Success -> {
                    response.value.body?.let { authToken ->
                        Session.authToken = authToken
                        fetchUser(userId = userId, onSignedIn = onSignedIn, onNoSignIn = onNoSignIn)
                    }
                }
            }
        }
    }

    private fun createNewToken(userId: String, authToken: AuthToken, onSignedIn: () -> Unit, onNoSignIn: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            when(val response = remoteDataSource.createNewToken(authToken)) {
                is ResultWrapper.NetworkError -> {
                    onNoSignIn.invoke()
                }
                is ResultWrapper.HttpError -> {
                    onNoSignIn.invoke()
                }
                is ResultWrapper.UnAuthError -> {
                    onNoSignIn.invoke()
                }
                is ResultWrapper.Success -> {
                    Session.authToken = response.value.body!!
                    fetchUser(userId = userId, onSignedIn = onSignedIn, onNoSignIn = onNoSignIn)
                }
            }
        }
    }

    private fun fetchUser(userId: String, onSignedIn: () -> Unit, onNoSignIn: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            when(val response = remoteDataSource.getUser(userId = userId)) {
                is ResultWrapper.NetworkError -> {
                    onNoSignIn.invoke()
                }
                is ResultWrapper.HttpError -> {
                    onNoSignIn.invoke()
                }
                is ResultWrapper.UnAuthError -> {
                    onNoSignIn.invoke()
                }
                is ResultWrapper.Success -> {
                    response.value.body?.let { user ->
                        Session.user = user
                        onSignedIn.invoke()
                    }
                }
            }
        }
    }
}