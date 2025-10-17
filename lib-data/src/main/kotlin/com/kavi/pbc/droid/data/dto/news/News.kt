package com.kavi.pbc.droid.data.dto.news

data class News(
    val id: String,
    val title: String,
    val content: String,
    val newsStatus: NewsStatus,
    val facebookLink: String? = null
)
