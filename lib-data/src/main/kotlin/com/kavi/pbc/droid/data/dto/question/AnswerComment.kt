package com.kavi.pbc.droid.data.dto.question

import com.kavi.pbc.droid.data.dto.user.UserSummary
import kotlinx.serialization.Serializable

@Serializable
data class AnswerComment(
    val comment: String,
    val createdTime: Long,
    val author: UserSummary
)
