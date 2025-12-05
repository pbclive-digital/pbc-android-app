package com.kavi.pbc.droid.data.dto.news

import java.text.DateFormat
import java.util.Date

data class News(
    val id: String,
    val title: String,
    val content: String,
    val newsStatus: NewsStatus,
    val facebookLink: String? = null,
    val createdTime: Long,
    var publishedTime: Long = 0
) {
    fun getFormatCreatedDate(): String {
        val dateFormat = DateFormat.getDateInstance()
        return dateFormat.format(Date(createdTime))
    }

    fun getFormatPublishedDate(): String {
        val dateFormat = DateFormat.getDateInstance()
        return dateFormat.format(Date(publishedTime))
    }
}
