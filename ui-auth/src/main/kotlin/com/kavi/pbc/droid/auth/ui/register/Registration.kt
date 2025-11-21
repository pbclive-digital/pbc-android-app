package com.kavi.pbc.droid.auth.ui.register

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kavi.pbc.droid.auth.R
import com.kavi.pbc.droid.lib.common.ui.component.AppFilledButton
import com.kavi.pbc.droid.lib.common.ui.component.AppFullScreenLoader
import com.kavi.pbc.droid.lib.common.ui.component.Title
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily

@Composable
fun RegisterUI(navController: NavController, email: String, viewModel: RegistrationViewModel = hiltViewModel()) {

    val signedUsed by viewModel.signedUsed.collectAsState()
    // Create the user from sign-in email
    viewModel.createUserFromFirebaseAuth(email = email)
    val onUserCreated by viewModel.onUserCreated.collectAsState()

    var firstName by remember { mutableStateOf(viewModel.signedUsed.value.firstName) }
    var lastName by remember { mutableStateOf(viewModel.signedUsed.value.lastName) }
    var phoneNumber by remember { mutableStateOf(viewModel.signedUsed.value.phoneNumber) }
    var address by remember { mutableStateOf(viewModel.signedUsed.value.address) }

    val isLoading by remember { mutableStateOf(false) }

    Box (
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = 56.dp, start = 16.dp, end = 16.dp, bottom = 24.dp)
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Title(titleText = stringResource(R.string.label_register))

            Column (
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = stringResource(R.string.phrase_welcome_msg),
                    fontFamily = PBCFontFamily,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .clip(CircleShape)
                            .border(
                                border = BorderStroke(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.tertiary
                                ),
                                shape = CircleShape
                            )
                    ) {
                        AsyncImage(
                            model = signedUsed.profilePicUrl,
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(160.dp)
                                .padding(5.dp)
                                .clip(CircleShape)
                        )
                    }
                }

                Text(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(),
                    text = signedUsed.email,
                    fontFamily = PBCFontFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                OutlinedTextField(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    value = firstName ?: run { "" },
                    maxLines = 1,
                    label = { Text(stringResource(R.string.label_first_name)) },
                    onValueChange = { newValue ->
                        firstName = newValue
                    }
                )

                OutlinedTextField(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    value = lastName ?: run { "" },
                    maxLines = 1,
                    label = { Text(stringResource(R.string.label_last_name)) },
                    onValueChange = { newValue ->
                        lastName = newValue
                    }
                )

                OutlinedTextField(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    value = phoneNumber ?: run { "" },
                    maxLines = 1,
                    label = { Text(stringResource(R.string.label_phone_num)) },
                    onValueChange = { newValue ->
                        phoneNumber = newValue
                    }
                )

                OutlinedTextField(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    value = address ?: run { "" },
                    maxLines = 3,
                    singleLine = false,
                    label = { Text(stringResource(R.string.label_address)) },
                    onValueChange = { newValue ->
                        address = newValue
                    }
                )

                Spacer(modifier = Modifier.weight(1f))

                AppFilledButton(
                    label = stringResource(R.string.label_register),
                    modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
                ) {
                    viewModel.registerNewUser(
                        firstName = firstName,
                        lastName = lastName,
                        phoneNum = phoneNumber,
                        address = address
                    )
                }
            }
        }
    }

    if(isLoading) {
        AppFullScreenLoader()
    }

    if (onUserCreated) {
        viewModel.resetOnUserCreated()
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