package com.kavi.pbc.droid.user.ui.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.data.dto.user.User
import com.kavi.pbc.droid.network.model.ResultWrapper
import com.kavi.pbc.droid.user.data.model.SearchModel
import com.kavi.pbc.droid.user.data.model.UserRoleUpdateReq
import com.kavi.pbc.droid.user.data.repository.UserRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserManageViewModel @Inject constructor(
    private val remoteDataSource: UserRemoteRepository
): ViewModel() {

    private val _nameOrEmailValidateState = MutableStateFlow(false)
    val nameOrEmailValidateState: StateFlow<Boolean> = _nameOrEmailValidateState

    private val _userTypeModificationStatus = MutableStateFlow(false)
    val userTypeModificationStatus: StateFlow<Boolean> = _userTypeModificationStatus

    private val _userResultList = MutableStateFlow<MutableList<User>>(mutableListOf())
    val userResultList: StateFlow<MutableList<User>> = _userResultList

    private val _selectedUser = MutableStateFlow(User(email = ""))
    val selectedUser: StateFlow<User> = _selectedUser

    private var givenName = ""
    private var givenEmail = ""

    fun findUser(nameOrEmail: String?) {
        when(validateNameOrEmail(nameOrEmail = nameOrEmail)) {
            SearchModel.EMAIL_SEARCH -> fetchUserByEmail()
            SearchModel.NAME_SEARCH -> fetchUserByName()
            SearchModel.INVALID -> {
                _nameOrEmailValidateState.value = false
            }
        }
    }

    fun modifyUserRole(newUserRole: String, residentMonkFlag: Boolean, user: User) {

        val userRoleUpdate = UserRoleUpdateReq(
            newRole = newUserRole,
            residentMonkFlag = residentMonkFlag,
            user = user
        )

        viewModelScope.launch {
            when(val response = remoteDataSource.modifyUserType(userRoleUpdate)) {
                is ResultWrapper.NetworkError -> {}
                is ResultWrapper.HttpError -> {}
                is ResultWrapper.UnAuthError -> {}
                is ResultWrapper.Success -> {
                    response.value.body?.let {
                        _selectedUser.value = it
                        updateGivenUserInResultList(it)
                        _userTypeModificationStatus.value = true
                    }
                }
            }
        }
    }

    fun revokeUserRoleModificationStatus() {
        _userTypeModificationStatus.value = false
    }

    fun updateSelectedUser(selectedUser: User) {
        _selectedUser.value = selectedUser
    }

    private fun updateGivenUserInResultList(user: User) {
        val newList = _userResultList.value
            .filterNot { it.id == user.id }
            .toMutableList()

        newList.add(user)
        _userResultList.value = newList
    }

    private fun fetchUserByEmail() {
        viewModelScope.launch {
            when(val response = remoteDataSource.getUserByEmail(email = givenEmail)) {
                is ResultWrapper.NetworkError -> {}
                is ResultWrapper.HttpError -> {}
                is ResultWrapper.UnAuthError -> {}
                is ResultWrapper.Success -> {
                    response.value.body?.let {
                        val dataList = mutableListOf(it)
                        _userResultList.value = dataList
                    }
                }
            }
        }
    }

    private fun fetchUserByName() {
        viewModelScope.launch {
            when(val response = remoteDataSource.getUserByName(name = givenName)) {
                is ResultWrapper.NetworkError -> {}
                is ResultWrapper.HttpError -> {}
                is ResultWrapper.UnAuthError -> {}
                is ResultWrapper.Success -> {
                    response.value.body?.let {
                        _userResultList.value = it
                    }
                }
            }
        }
    }

    private fun validateNameOrEmail(nameOrEmail: String?): SearchModel {
        nameOrEmail?.let {
            if (isValidEmail(it)) {
                givenEmail = it
                return SearchModel.EMAIL_SEARCH
            } else {
                givenName = it
                return SearchModel.NAME_SEARCH
            }
        }?: run {
            return SearchModel.INVALID
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9\\+\\.\\_\\%\\-]+@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+")
        return email.matches(emailRegex)
    }
}