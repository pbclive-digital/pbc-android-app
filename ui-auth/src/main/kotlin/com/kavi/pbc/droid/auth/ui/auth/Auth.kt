package com.kavi.pbc.droid.auth.ui.auth

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.kavi.droid.color.palette.extension.quaternary
import com.kavi.pbc.droid.auth.AuthConstant
import com.kavi.pbc.droid.auth.R
import com.kavi.pbc.droid.lib.common.ui.component.Loader
import com.kavi.pbc.droid.lib.common.ui.theme.PBCNameFontFamily
import kotlinx.coroutines.launch

@Composable
fun AuthUI(navController: NavController, viewModel: AuthViewModel = hiltViewModel()) {

    val onUnRegistered by viewModel.onUnRegistered.collectAsState()
    val onSignedIn by viewModel.onSignedIn.collectAsState()
    val signedUser by viewModel.signedUser.collectAsState()

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val isLoading = remember { mutableStateOf(false) }

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(com.kavi.pbc.droid.lib.common.ui.R.drawable.icon_pbc),
                contentDescription = "Pittsburgh Buddhist Center icon",
                modifier = Modifier
                    .size(240.dp)
                    .clip(CircleShape)
            )

            Text(
                stringResource(com.kavi.pbc.droid.lib.common.ui.R.string.label_pbc_part_one),
                fontFamily = PBCNameFontFamily,
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.quaternary,
                modifier = Modifier.padding(top = 12.dp)
            )

            Text(
                stringResource(com.kavi.pbc.droid.lib.common.ui.R.string.label_pbc_part_two),
                fontFamily = PBCNameFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, bottom = 32.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(5.dp),
                onClick = {
                    scope.launch {
                        signInWithGoogle(
                            context = context,
                            viewModel = viewModel,
                            showLoader = isLoading,
                        )
                    }
                }
            ) {
                Row (
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_google),
                        contentDescription = "",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(4.dp)
                    )
                    Text(
                        text = stringResource(R.string.label_login_with_google),
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(4.dp),
                    )
                }
            }

            Text(
                text = stringResource(R.string.label_login_as_guest),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .clickable {
                        navigateToDashboard(navController = navController)
                    }
            )
        }
    }

    if(isLoading.value) {
        Loader()
    }

    if (onUnRegistered) {
        isLoading.value = false
        // This avoid re-navigate back to registration screen when user tap back from registration.
        viewModel.resetRegistrationUINavigation()
        navController.navigate("auth/registration-ui/${signedUser.email}")
    }

    if (onSignedIn) {
        isLoading.value = false
        viewModel.resetDashboardUINavigation()
        navigateToDashboard(navController = navController)
    }
}

private fun navigateToDashboard(navController: NavController) {
    navController.navigate("auth/to/dashboard") {
        // Remove AuthUI from backstack
        popUpTo("auth/auth-ui") {
            inclusive = true
        }
    }
}

private suspend fun signInWithGoogle(context: Context, viewModel: AuthViewModel, showLoader: MutableState<Boolean>) {
    val credentialManager = CredentialManager.create(context = context)

    val googleIdOption = GetGoogleIdOption.Builder()
        .setServerClientId(AuthConstant.SERVICE_CLIENT_ID)
        .setFilterByAuthorizedAccounts(false)
        .build()

    val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    try {
        val request = credentialManager.getCredential(context = context, request = request)
        if (request.credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(request.credential.data)
            val idToken = googleIdTokenCredential.idToken

            val credential = GoogleAuthProvider.getCredential(idToken, null)

            Firebase.auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showLoader.value = true
                        val user = task.result.user
                        viewModel.fetchUserStatus(user?.email!!, user.uid)
                    } else {
                        Log.e("Google_SignIn_Error", task.exception.toString())
                    }
                }
        } else {
            Log.e("Google_SignIn_Error", "Failure")
        }
    } catch (ex: GetCredentialException) {
        Log.e("Google_SignIn_Error", ex.printStackTrace().toString())
    }
}

@Preview
@Composable
fun AuthUI_Preview() {
    AuthUI(navController = rememberNavController())
}