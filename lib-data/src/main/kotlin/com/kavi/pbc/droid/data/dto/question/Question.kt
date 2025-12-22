package com.kavi.pbc.droid.data.dto.question

import com.kavi.pbc.droid.data.dto.user.User
import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: String? = null,
    var title: String = "",
    var content: String = "",
    val createdTime: Long = 0,
    val authorId: String = "",
    val author: User = User(email = ""),
    val answerList: MutableList<AnswerComment> = mutableListOf()
)
