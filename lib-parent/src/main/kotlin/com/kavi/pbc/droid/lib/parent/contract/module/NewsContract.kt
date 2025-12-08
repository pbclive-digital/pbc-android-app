package com.kavi.pbc.droid.lib.parent.contract.module

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.kavi.pbc.droid.data.dto.news.News
import com.kavi.pbc.droid.lib.parent.contract.CommonContract

interface NewsContract: CommonContract {

    @Composable
    fun RetrieveNavGraphWithDynamicDestination(startDestination: String)

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun RetrieveSelectedNewsSheet(sheetState: SheetState, showSheet: MutableState<Boolean>, selectedNews: News)
}