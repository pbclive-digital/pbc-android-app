package com.kavi.pbc.droid.data.dto.event.signup

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class SignUpSheet(
    val sheetId: String = UUID.randomUUID().toString(),
    val sheetName: String = "",
    val sheetDescription: String = "",
    val availableCount: Int = 0,
    val allowMultiSignUps: Boolean = false,
)