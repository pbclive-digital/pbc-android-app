package com.kavi.pbc.droid.profile.ui.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.data.dto.user.User
import com.kavi.pbc.droid.network.model.ResultWrapper
import com.kavi.pbc.droid.network.session.Session
import com.kavi.pbc.droid.profile.data.model.ProfileUpdateStatus
import com.kavi.pbc.droid.profile.data.repository.local.ProfileLocalRepository
import com.kavi.pbc.droid.profile.data.repository.remote.ProfileRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    val localRepository: ProfileLocalRepository,
    val remoteRepository: ProfileRemoteRepository
): ViewModel() {
    private val _profile = MutableStateFlow(User(email = ""))
    val profile: StateFlow<User> = _profile

    private val _profileUpdateStatus = MutableStateFlow(ProfileUpdateStatus.NONE)
    val profileUpdateStatus: StateFlow<ProfileUpdateStatus> = _profileUpdateStatus

    fun setModifyProfile(profileKey: String) {
        localRepository.getModifyingProfile(profileKey).onSuccess { userProfile ->
            _profile.value = userProfile
        }
    }

    fun updateFirstName(firstName: String) {
        _profile.value.firstName = firstName
    }

    fun updateLastName(lastName: String) {
        _profile.value.lastName = lastName
    }

    fun updatePhoneNumber(phoneNumber: String) {
        _profile.value.phoneNumber = phoneNumber
    }

    fun updateAddress(address: String) {
        _profile.value.address = address
    }

    fun updateProfile() {
        _profileUpdateStatus.value = ProfileUpdateStatus.PENDING
        viewModelScope.launch {
            when(val response = remoteRepository
                .updateUserAccount(userId = _profile.value.id!!, user = _profile.value)) {
                is ResultWrapper.NetworkError, is ResultWrapper.HttpError, is ResultWrapper.UnAuthError -> {
                    _profileUpdateStatus.value = ProfileUpdateStatus.FAILURE
                }
                is ResultWrapper.Success -> {
                    response.value.body?.let {
                        Session.user = it
                        _profileUpdateStatus.value = ProfileUpdateStatus.SUCCESS
                    }
                }
            }
        }
    }
}