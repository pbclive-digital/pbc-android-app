package com.kavi.pbc.droid.profile.ui.update

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kavi.pbc.droid.lib.common.ui.component.AppFilledButton
import com.kavi.pbc.droid.lib.common.ui.component.AppFullScreenLoader
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineMultiLineTextField
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineTextField
import com.kavi.pbc.droid.lib.common.ui.component.Title
import com.kavi.pbc.droid.profile.R
import com.kavi.pbc.droid.profile.data.model.ProfileUpdateStatus
import javax.inject.Inject

class ProfileUpdate @Inject constructor() {

    @Composable
    fun ProfileUpdateUI(navController: NavController,
                        modifyProfileKey: String? = null,
                        viewModel: UpdateProfileViewModel = hiltViewModel()) {

        val context = LocalContext.current

        modifyProfileKey?.let {
            viewModel.setModifyProfile(it)
        }

        val profileUser by viewModel.profile.collectAsState()
        val profileUpdateStatus by viewModel.profileUpdateStatus.collectAsState()

        val firstName = remember { mutableStateOf(TextFieldValue(
            profileUser.firstName ?: run { "" })) }
        val lastName = remember { mutableStateOf(TextFieldValue(
            profileUser.lastName ?: run { "" })) }
        val phoneNumber = remember { mutableStateOf(TextFieldValue(
            profileUser.phoneNumber ?: run { "" })) }
        val address = remember { mutableStateOf(TextFieldValue(
            profileUser.address ?: run { "" })) }

        Box {
            Box (
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(top = 56.dp, start = 16.dp, end = 16.dp)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Title(
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                        titleText = stringResource(R.string.label_profile_edit),
                    )

                    Column (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 12.dp, end = 12.dp, top = 20.dp, bottom = 30.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        AppOutlineTextField (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            headingText = stringResource(R.string.label_first_name).uppercase(),
                            contentText = firstName,
                            onValueChange = { newValue ->
                                firstName.value = newValue
                                viewModel.updateFirstName(firstName.value.text)
                            }
                        )

                        AppOutlineTextField (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            headingText = stringResource(R.string.label_last_name).uppercase(),
                            contentText = lastName,
                            onValueChange = { newValue ->
                                lastName.value = newValue
                                viewModel.updateLastName(lastName.value.text)
                            }
                        )

                        AppOutlineTextField (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            headingText = stringResource(R.string.label_phone).uppercase(),
                            contentText = phoneNumber,
                            onValueChange = { newValue ->
                                phoneNumber.value = newValue
                                viewModel.updatePhoneNumber(phoneNumber.value.text)
                            }
                        )

                        AppOutlineMultiLineTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            maxLines = 4,
                            headingText = stringResource(R.string.label_address).uppercase(),
                            contentText = address,
                            onValueChange = { newValue ->
                                address.value = newValue
                                viewModel.updateAddress(address.value.text)
                            }
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        AppFilledButton(
                            label = stringResource(R.string.label_profile_update)
                        ) {
                            viewModel.updateProfile()
                        }
                    }
                }
            }

            when(profileUpdateStatus) {
                ProfileUpdateStatus.NONE -> {}
                ProfileUpdateStatus.PENDING -> {
                    AppFullScreenLoader()
                }
                ProfileUpdateStatus.FAILURE -> {
                    Toast.makeText(context, stringResource(R.string.label_profile_update_failure), Toast.LENGTH_LONG).show()
                }
                ProfileUpdateStatus.SUCCESS -> {
                    Toast.makeText(context, stringResource(R.string.label_profile_update_success), Toast.LENGTH_LONG).show()
                    navController.popBackStack()
                }
            }
        }
    }
}