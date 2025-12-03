package com.kavi.pbc.droid.user.ui.manage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kavi.droid.color.palette.extension.shadow
import com.kavi.pbc.droid.data.dto.user.UserType
import com.kavi.pbc.droid.lib.common.ui.component.AppDropDownMenu
import com.kavi.pbc.droid.lib.common.ui.component.AppFilledButton
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import com.kavi.pbc.droid.user.R
import javax.inject.Inject

class UserFunctionBottomSheet @Inject constructor() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ChangeUserRoleBottomSheet(sheetState: SheetState,
                                  showSheet: MutableState<Boolean>,
                                  viewModel: UserManageViewModel = hiltViewModel()) {

        val selectedUser by viewModel.selectedUser.collectAsState()
        val userType = remember { mutableStateOf(selectedUser.userType.name) }
        var isResidentMonkChecked by remember { mutableStateOf(selectedUser.residentMonk) }
        val userTypeUpdateStatus by viewModel.userTypeModificationStatus.collectAsState()

        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                showSheet.value = false
            },
            containerColor = MaterialTheme.colorScheme.background,
            scrimColor = MaterialTheme.colorScheme.shadow.copy(alpha = .5f)
        ) {
            Box (
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(start = 20.dp, end = 20.dp, bottom = 40.dp)
                    .fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.label_user_modify_role),
                        fontFamily = PBCFontFamily,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(2.dp),
                        thickness = 2.dp
                    )

                    Text(
                        text = stringResource(R.string.phrase_user_modify_role),
                        fontFamily = PBCFontFamily,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )

                    AppDropDownMenu(
                        modifier = Modifier
                            .padding(top = 12.dp),
                        title = stringResource(R.string.label_user_user_type).uppercase(),
                        selectableItems = listOf(UserType.ADMIN.name, UserType.MANAGER.name,
                            UserType.MONK.name, UserType.CONSUMER.name),
                        selectedItem = userType,
                    )

                    if (userType.value == UserType.MONK.name) {
                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, start = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.label_user_resident_monk),
                                fontFamily = PBCFontFamily,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Checkbox(
                                checked = isResidentMonkChecked,
                                onCheckedChange = { newCheckedState ->
                                    isResidentMonkChecked = newCheckedState
                                }
                            )
                        }
                    }

                    AppFilledButton(
                        modifier = Modifier.padding(top = 12.dp),
                        label = stringResource(com.kavi.pbc.droid.lib.common.ui.R.string.label_modify)
                    ) {
                        viewModel.modifyUserRole(newUserRole = userType.value,
                            residentMonkFlag = isResidentMonkChecked, user = selectedUser)
                    }
                }
            }
        }

        if (userTypeUpdateStatus) {
            showSheet.value = false
            viewModel.revokeUserRoleModificationStatus()
        }
    }
}