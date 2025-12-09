package com.kavi.pbc.droid.profile.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.data.dto.user.User
import com.kavi.pbc.droid.network.model.ResultWrapper
import com.kavi.pbc.droid.network.session.Session
import com.kavi.pbc.droid.profile.data.repository.ProfileRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val remoteDataSource: ProfileRemoteRepository
): ViewModel() {
    private val _profileUser = MutableStateFlow(Session.user!!)
    val profileUser: StateFlow<User> = _profileUser

    private val _isUserDeleted = MutableStateFlow(false)
    val isUserDeleted: StateFlow<Boolean> = _isUserDeleted

    fun deleteUserAccount() {
        viewModelScope.launch {
            when(val response = remoteDataSource.deleteUserAccount(_profileUser.value.id!!)) {
                is ResultWrapper.NetworkError -> {
                    Session.authToken = null
                }
                is ResultWrapper.HttpError -> {
                    Session.authToken = null
                }
                is ResultWrapper.UnAuthError -> {}
                is ResultWrapper.Success -> {
                    _isUserDeleted.value = true
                }
            }
        }
    }

    fun resetUserDeletionStatus() {
        _isUserDeleted.value = false
    }
}