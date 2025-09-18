package com.kavi.pbc.droid.data.dto.quote

import com.kavi.pbc.droid.data.dto.quote.Quote
import kotlinx.serialization.Serializable

@Serializable
data class DailyQuote(
    val dailyQuoteList: List<Quote>,
    val date: Long
)