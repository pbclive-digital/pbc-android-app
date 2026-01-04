package com.kavi.pbc.droid.data.dto.event.signup

import kotlinx.serialization.Serializable

@Serializable
data class EventSignUpSheetList(
    val id: String,
    val signUpSheetItemList: MutableList<EventSignUpSheet> = mutableListOf()
)
