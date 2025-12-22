package com.kavi.pbc.droid.data.dto.question

import com.kavi.pbc.droid.data.dto.user.UserSummary
import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.util.Date

@Serializable
data class AnswerComment(
    val comment: String,
    val createdTime: Long,
    val author: UserSummary
) {
    fun getFormatCreatedDate(): String {
        val dateFormat = DateFormat.getDateInstance()
        return dateFormat.format(Date(createdTime))
    }
}
