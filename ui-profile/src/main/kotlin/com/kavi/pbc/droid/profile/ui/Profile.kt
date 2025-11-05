package com.kavi.pbc.droid.profile.ui

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kavi.droid.color.palette.extension.shadow
import com.kavi.pbc.droid.data.dto.user.User
import com.kavi.pbc.droid.lib.common.ui.component.AppFilledButton
import com.kavi.pbc.droid.lib.common.ui.component.AppLinkButton
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineButton
import com.kavi.pbc.droid.lib.common.ui.component.Title
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import com.kavi.pbc.droid.lib.parent.contract.ContractServiceLocator
import com.kavi.pbc.droid.lib.parent.contract.module.AuthContract
import com.kavi.pbc.droid.profile.R
import com.kavi.pbc.droid.profile.ui.dialog.DeleteConfirmationDialog
import com.kavi.pbc.droid.profile.ui.dialog.SignOutConfirmationDialog
import javax.inject.Inject

class Profile @Inject constructor() {

    @Composable
    fun ProfileUI(navController: NavController, modifier: Modifier = Modifier, viewModel: ProfileViewModel = hiltViewModel()) {

        val profileUser by viewModel.profileUser.collectAsState()
        val isUserDeleted by viewModel.isUserDeleted.collectAsState()

        val showSignOutConfirmationDialog = remember { mutableStateOf(false) }
        val showDeleteConfirmationDialog = remember { mutableStateOf(false) }

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
                    titleText = stringResource(R.string.label_profile),
                )

                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 12.dp, end = 12.dp, top = 20.dp, bottom = 30.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(140.dp)
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
                                model = profileUser.profilePicUrl,
                                contentDescription = "Profile Picture",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(140.dp)
                                    .padding(5.dp)
                                    .clip(CircleShape)
                            )
                        }
                    }

                    // Basic information block
                    BasicInfoCard(profileUser = profileUser)

                    // User favorites block
                    UserFavorites()

                    Spacer(modifier = Modifier.weight(1f))

                    AppFilledButton(
                        modifier = Modifier.padding(top = 40.dp),
                        label = stringResource(R.string.label_sign_out)
                    ) {
                        showSignOutConfirmationDialog.value = true
                    }

                    AppOutlineButton(
                        modifier = Modifier.padding(top = 10.dp),
                        label = stringResource(R.string.label_delete_acc)
                    ) {
                        showDeleteConfirmationDialog.value = true
                    }
                }
            }
        }

        if (isUserDeleted) {
            viewModel.resetUserDeletionStatus()
            navigateToAuth(navController = navController)
        }

        SignOutConfirmationDialog(
            showDialog = showSignOutConfirmationDialog,
            onAgree = {
                showSignOutConfirmationDialog.value = false
                navigateToAuth(navController = navController)
            },
            onDisagree = {
                showSignOutConfirmationDialog.value = false
            }
        )

        DeleteConfirmationDialog(
            showDialog = showDeleteConfirmationDialog,
            onAgree = {
                showDeleteConfirmationDialog.value = false
                viewModel.deleteUserAccount()
            },
            onDisagree = {
                showDeleteConfirmationDialog.value = false
            }
        )
    }

    private fun navigateToAuth(navController: NavController) {
        ContractServiceLocator.locate(AuthContract::class).signOut()
        navController.navigate("profile/to/auth") {
            popUpTo(0) { inclusive = true }
        }
    }
}

@Composable
private fun BasicInfoCard(profileUser: User) {
    Card(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = MaterialTheme.colorScheme.shadow
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column (
            modifier = Modifier.padding(16.dp)
        ) {
            Row {
                Text(
                    text = stringResource(R.string.label_basic_info),
                    fontFamily = PBCFontFamily,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    fontSize = 22.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                AppLinkButton(
                    label = stringResource(com.kavi.pbc.droid.lib.common.ui.R.string.label_edit),
                    labelTextSize = 18.sp,
                    color = MaterialTheme.colorScheme.secondary,
                ) {
                    // Open dialog box to edit details
                }
            }

            Text(
                text = stringResource(R.string.phrase_basic_info),
                fontFamily = PBCFontFamily,
                fontWeight = FontWeight.Light,
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            )

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.label_email),
                    fontFamily = PBCFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    modifier = Modifier.width(100.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = profileUser.email,
                    fontFamily = PBCFontFamily,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.End,
                    fontSize = 18.sp,
                )
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.label_name),
                    fontFamily = PBCFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    modifier = Modifier.width(100.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "${profileUser.firstName} ${profileUser.lastName}",
                    fontFamily = PBCFontFamily,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.End,
                    fontSize = 18.sp,
                )
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.label_phone),
                    fontFamily = PBCFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    modifier = Modifier.width(100.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = profileUser.phoneNumber ?: run { "" },
                    fontFamily = PBCFontFamily,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.End,
                    fontSize = 18.sp,
                )
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.label_address),
                    fontFamily = PBCFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    modifier = Modifier.width(100.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = profileUser.address ?: run { "" },
                    fontFamily = PBCFontFamily,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.End,
                    fontSize = 18.sp,
                )
            }
        }
    }
}

@Composable
private fun UserFavorites() {
    Card(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = MaterialTheme.colorScheme.shadow
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column (
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.label_favorite),
                fontFamily = PBCFontFamily,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                fontSize = 22.sp
            )

            Text(
                text = stringResource(R.string.phrase_favorite),
                fontFamily = PBCFontFamily,
                fontWeight = FontWeight.Light,
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            )
        }
    }
}