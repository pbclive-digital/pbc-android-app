package com.kavi.pbc.droid.lib.parent.extension

import android.content.Context
import android.content.res.Configuration

fun getDeviceFormFactor(context: Context): String {
    val xlarge = context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == 4
    val large = context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_LARGE

    return if (xlarge)
        "INCH10TAB"
    else if (large)
        "INCH7TAB"
    else
        "PHONE"
}