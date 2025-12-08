package com.kavi.pbc.droid.news

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.kavi.pbc.droid.data.dto.news.News
import com.kavi.pbc.droid.lib.parent.contract.module.NewsContract
import com.kavi.pbc.droid.news.navigation.NewsNavigation
import com.kavi.pbc.droid.news.ui.selected.NewsSelected
import javax.inject.Inject

class NewsContractImpl @Inject constructor(): NewsContract {

    @Inject
    lateinit var newsNavigation: NewsNavigation

    @Inject
    lateinit var newsSelected: NewsSelected

    @Composable
    override fun RetrieveNavGraph() {
        newsNavigation.NewsNavGraph()
    }

    @Composable
    override fun RetrieveNavGraphWithDynamicDestination(startDestination: String) {
        newsNavigation.NewsNavGraph(startDestination = startDestination)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun RetrieveSelectedNewsSheet(sheetState: SheetState,
                                           showSheet: MutableState<Boolean>,
                                           selectedNews: News) {
        newsSelected.NewsSelectedBottomSheetUI(sheetState = sheetState,
            showSheet = showSheet, selectedNews = selectedNews)
    }
}