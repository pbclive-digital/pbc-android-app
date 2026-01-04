package com.kavi.pbc.droid.data.dto.event.signup

import kotlinx.serialization.Serializable

@Serializable
data class EventSignUpSheet(
    val sheetId: String = "",
    val sheetName: String = "",
    val sheetDescription: String = "",
    val availableCount: Int = 0,
    val contributorList: MutableList<EventSignUpSheetContributor> = mutableListOf()
)
