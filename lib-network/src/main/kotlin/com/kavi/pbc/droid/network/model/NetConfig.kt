package com.kavi.pbc.droid.network.model

import okhttp3.Interceptor

data class NetConfig(
    val schema: String,
    val domain: String,
    val baseUrl: String = "$schema://$domain",
    val networkInterceptors: List<Interceptor>? = null
)
