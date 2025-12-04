package com.kavi.pbc.droid.news

import androidx.compose.runtime.Composable
import com.kavi.pbc.droid.lib.parent.contract.module.NewsContract
import com.kavi.pbc.droid.news.navigation.NewsNavigation
import javax.inject.Inject

class NewsContractImpl @Inject constructor(): NewsContract {

    @Inject
    lateinit var newsNavigation: NewsNavigation

    @Composable
    override fun RetrieveNavGraph() {
        newsNavigation.NewsNavGraph()
    }
}