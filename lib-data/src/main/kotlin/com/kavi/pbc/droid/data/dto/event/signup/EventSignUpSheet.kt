package com.kavi.pbc.droid.data.dto.event.signup

import kotlinx.serialization.Serializable

@Serializable
data class EventSignUpSheet(
    val id: String,
    val signUpSheetItemList: MutableList<SignUpSheetItem> = mutableListOf()
)
