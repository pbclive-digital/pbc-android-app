package com.kavi.pbc.droid.data.dto.news

import com.kavi.pbc.droid.data.dto.user.UserSummary
import java.text.DateFormat
import java.util.Date

data class News(
    val id: String? = null,
    var title: String = "",
    var content: String = "",
    val newsStatus: NewsStatus = NewsStatus.DRAFT,
    var facebookLink: String? = null,
    var newsImage: String? = null,
    val createdTime: Long = 0,
    var publishedTime: Long = 0,
    var author: UserSummary = UserSummary()
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
