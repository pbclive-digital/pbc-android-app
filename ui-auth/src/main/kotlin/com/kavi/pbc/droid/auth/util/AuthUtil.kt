package com.kavi.pbc.droid.auth.util

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.kavi.pbc.droid.data.dto.user.User
import com.kavi.pbc.droid.data.dto.user.UserAuthType

object AuthUtil {
    fun createUserFromFirebaseAuth(email: String): User {
        val user = User(email = email)

        val currentUser = Firebase.auth.currentUser

        user.id = currentUser?.uid
        val displayNames = currentUser?.displayName?.split(" ")
        displayNames?.let {
            user.firstName = it.first()
            user.lastName = it.last()
        }
        user.userAuthType = UserAuthType.GOOGLE
        user.phoneNumber = currentUser?.phoneNumber
        user.profilePicUrl = currentUser?.photoUrl.toString()

        return user
    }
}