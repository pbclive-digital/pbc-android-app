package com.kavi.pbc.droid.network.model

import okhttp3.Interceptor

data class NetConfig(
    val scheme: String,
    val domain: String,
    val baseUrl: String = "$scheme://$domain",
    val networkInterceptors: List<Interceptor>? = null
)
