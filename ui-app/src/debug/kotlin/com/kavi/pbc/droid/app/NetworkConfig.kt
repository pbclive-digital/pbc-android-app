package com.kavi.pbc.droid.app

import com.kavi.pbc.droid.network.model.NetConfig

object NetworkConfig {

    //val networkConfig = NetConfig("http://10.0.2.2:8082", networkInterceptor)
    val networkConfig = NetConfig(
        schema = "http", domain = "192.168.40.22:8082"
    )
}