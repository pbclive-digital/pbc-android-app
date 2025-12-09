package com.kavi.pbc.droid.profile.data.repository.local

import com.kavi.pbc.droid.data.dto.user.User
import com.kavi.pbc.droid.lib.datastore.AppInMemoryStore
import java.util.UUID
import javax.inject.Inject

class ProfileLocalRepository @Inject constructor(
    private val inMemoryStore: AppInMemoryStore
) {
    fun setModifyingProfile(user: User): String {
        val randomProfileKey = UUID.randomUUID().toString()
        inMemoryStore.storeValue(randomProfileKey, user)
        return randomProfileKey
    }

    fun getModifyingProfile(tempProfileKey: String): Result<User> {
        val profile = inMemoryStore.retrieveValue<User>(key = tempProfileKey)
        return profile
    }
}