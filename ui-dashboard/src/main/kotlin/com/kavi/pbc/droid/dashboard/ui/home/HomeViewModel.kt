package com.kavi.pbc.droid.dashboard.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.dashboard.data.repository.local.DashboardLocalRepository
import com.kavi.pbc.droid.dashboard.data.repository.remote.DashboardRemoteRepository
import com.kavi.pbc.droid.data.dto.quote.Quote
import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.data.dto.news.News
import com.kavi.pbc.droid.data.dto.quote.DailyQuote
import com.kavi.pbc.droid.lib.parent.util.DateTimeUtil
import com.kavi.pbc.droid.network.model.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val remoteDataSource: DashboardRemoteRepository,
    private val localDataRepository: DashboardLocalRepository
): ViewModel() {

    private val _dashboardEventList = MutableStateFlow<List<Event>>(mutableListOf())
    val dashboardEventList: StateFlow<List<Event>> = _dashboardEventList

    private val _dashboardQuoteList = MutableStateFlow<List<Quote>>(mutableListOf())
    val dashboardQuoteList: StateFlow<List<Quote>> = _dashboardQuoteList

    private val _dashboardNewsList = MutableStateFlow<List<News>>(mutableListOf())
    val dashboardNewsList: StateFlow<List<News>> = _dashboardNewsList

    fun fetchDashboardHomeEvents(isForceFetch: Boolean = false) {
        if (_dashboardEventList.value.isEmpty() || isForceFetch) {
            viewModelScope.launch {
                when (val response = remoteDataSource.getDashboardEvents()) {
                    is ResultWrapper.NetworkError -> {}
                    is ResultWrapper.HttpError -> {}
                    is ResultWrapper.UnAuthError -> {}
                    is ResultWrapper.Success -> {
                        response.value.body?.let {
                            _dashboardEventList.value = it
                        }
                    }
                }
            }
        }
    }

    fun getDashboardQuotes() {
        viewModelScope.launch {
            localDataRepository.retrieveDailyQuoteFromDatastore().collect { dailyQuote ->
                dailyQuote?.let {
                    if (DateTimeUtil.checkIsToday(dailyQuote.date)) {
                        _dashboardQuoteList.value = it.dailyQuoteList
                    } else {
                        fetchDashboardQuotesFromRemote()
                    }
                }?: run {
                    fetchDashboardQuotesFromRemote()
                }
            }
        }
    }

    fun getDashboardNews() {
        viewModelScope.launch {
            when (val response = remoteDataSource.getDashboardNews()) {
                is ResultWrapper.NetworkError -> {}
                is ResultWrapper.HttpError -> {}
                is ResultWrapper.UnAuthError -> {}
                is ResultWrapper.Success -> {
                    response.value.body?.let {
                        _dashboardNewsList.value = it
                    }
                }
            }
        }
    }

    private fun fetchDashboardQuotesFromRemote() {
        viewModelScope.launch {
            when(val response = remoteDataSource.getDashboardDailyQuotes()) {
                is ResultWrapper.NetworkError -> {}
                is ResultWrapper.HttpError -> {}
                is ResultWrapper.UnAuthError -> {}
                is ResultWrapper.Success -> {
                    response.value.body?.let {
                        _dashboardQuoteList.value = it
                        storeRetrievedQuotes(it)
                    }
                }
            }
        }
    }

    private fun storeRetrievedQuotes(quoteList: List<Quote>) {
        viewModelScope.launch {
            val dailyQuote = DailyQuote(dailyQuoteList = quoteList, System.currentTimeMillis())
            localDataRepository.storeDailyQuoteToDatastore(dailyQuote)
        }
    }
}