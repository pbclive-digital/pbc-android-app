package com.kavi.pbc.droid.data.dto.question

import com.kavi.pbc.droid.data.dto.user.User
import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: String,
    val title: String,
    val content: String,
    val createdTime: Long,
    val authorId: String,
    val author: User,
    val answerList: MutableList<AnswerComment> = mutableListOf()
)
