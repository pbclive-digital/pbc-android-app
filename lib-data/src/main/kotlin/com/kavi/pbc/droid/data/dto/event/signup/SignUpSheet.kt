package com.kavi.pbc.droid.data.dto.event.signup

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class SignUpSheet(
    val sheetId: String = UUID.randomUUID().toString(), // This ID is not sync with database. This use only for client purpose
    val sheetName: String = "",
    val sheetDescription: String = "",
    val availableCount: Int = 0
)