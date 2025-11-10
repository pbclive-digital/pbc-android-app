package com.kavi.pbc.droid.lib.parent.util

import java.time.LocalDate
import java.time.Instant
import java.time.ZoneId

object DateTimeUtil {

    fun getDateFromTimestamp(unixTimestamp: Long): LocalDate {
        val instant = Instant.ofEpochMilli(unixTimestamp)
        return instant.atZone(ZoneId.of("EST")).toLocalDate()
    }

    fun checkIsToday(date: Long): Boolean {
        return getDateFromTimestamp(date) == getDateFromTimestamp(System.currentTimeMillis())
    }
}