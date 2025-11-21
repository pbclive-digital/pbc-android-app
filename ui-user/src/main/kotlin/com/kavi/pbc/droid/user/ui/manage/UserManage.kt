package com.kavi.pbc.droid.user.ui.manage

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kavi.pbc.droid.lib.common.ui.component.AppFullScreenLoader
import com.kavi.pbc.droid.lib.common.ui.component.AppIconButton
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineTextField
import com.kavi.pbc.droid.lib.common.ui.component.Title
import com.kavi.pbc.droid.lib.common.ui.model.UIStatus
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import com.kavi.pbc.droid.user.R
import com.kavi.pbc.droid.user.ui.common.UserListItem
import javax.inject.Inject

class UserManage @Inject constructor() {

    @Inject
    lateinit var userFunctionBottomSheet: UserFunctionBottomSheet

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun UserManageUI(navController: NavController, viewModel: UserManageViewModel = hiltViewModel()) {

        val context = LocalContext.current

        val userNameOrEmail = remember { mutableStateOf(TextFieldValue("")) }
        val userResultList by viewModel.userResultList.collectAsState()

        val modifyUserRoleSheetState = rememberModalBottomSheetState()
        val showModifySheet = remember { mutableStateOf(false) }

        val viewUserSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
        val showViewSheet = remember { mutableStateOf(false) }

        val userSearchStatus by viewModel.userResultUiStatus.collectAsState()

        Box {
            Box(
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
                        titleText = stringResource(R.string.label_user_manage),
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 30.dp),
                        horizontalAlignment = Alignment.Companion.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.phrase_user_manage),
                            fontFamily = PBCFontFamily,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Companion.Justify,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        Row(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.Companion.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            AppOutlineTextField(
                                modifier = Modifier
                                    .padding(top = 8.dp),
                                headingText = stringResource(R.string.label_user_search_value).uppercase(),
                                contentText = userNameOrEmail,
                                onValueChange = { newValue ->
                                    userNameOrEmail.value = newValue
                                }
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            AppIconButton(
                                modifier = Modifier.padding(top = 12.dp),
                                icon = painterResource(R.drawable.icon_user_search),
                                buttonSize = 50.dp
                            ) {
                                viewModel.findUser(userNameOrEmail.value.text)
                            }

                            Spacer(modifier = Modifier.width(4.dp))

                            AppIconButton(
                                modifier = Modifier.padding(top = 12.dp),
                                icon = painterResource(R.drawable.icon_user_clear),
                                buttonSize = 50.dp
                            ) {
                                userNameOrEmail.value = TextFieldValue()
                                viewModel.clearSearchList()
                            }
                        }

                        if (userResultList.isNotEmpty()) {
                            LazyColumn (
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth()
                                    .padding(top = 20.dp)
                            ) {
                                items(userResultList) { user ->
                                    UserListItem(user = user, modifier = Modifier.padding(bottom = 4.dp), onModify = {
                                        viewModel.updateSelectedUser(user)
                                        showModifySheet.value = true
                                    }, onView = {
                                        viewModel.updateSelectedUser(user)
                                        showViewSheet.value = true
                                    })
                                }
                            }
                        } else {
                            Box (
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .padding(top = 20.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.background),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.label_user_search_empty),
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }
            }

            if (userSearchStatus == UIStatus.PENDING)
                AppFullScreenLoader()
        }

        if (showModifySheet.value) {
            userFunctionBottomSheet.ChangeUserRoleBottomSheet(sheetState = modifyUserRoleSheetState, showSheet = showModifySheet)
        }

        if (showViewSheet.value) {
            userFunctionBottomSheet.UserViewBottomSheet(sheetState = viewUserSheetState, showSheet = showViewSheet)
        }

        if (userSearchStatus == UIStatus.ERROR) {
            Toast.makeText(context, stringResource(R.string.label_user_search_error), Toast.LENGTH_LONG)
            viewModel.revokeUserSearchUiStatus()
        }
    }
}