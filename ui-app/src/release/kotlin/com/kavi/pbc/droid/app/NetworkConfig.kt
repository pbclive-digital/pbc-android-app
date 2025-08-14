package com.kavi.pbc.droid.app

import com.kavi.pbc.droid.network.model.NetConfig

object NetworkConfig {

    // TODO - Till the production deployment creates, this points to staging end-points
    val networkConfig = NetConfig(
        schema = "https", domain = "pbc-api-staging-1f3fe32cb947.herokuapp.com"
    )
}