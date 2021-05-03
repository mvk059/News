package com.mvk.news.ui.main

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import com.mvk.news.ui.base.BaseViewModel
import com.mvk.news.utils.common.Event
import com.mvk.news.utils.network.NetworkHelper
import com.mvk.news.utils.rx.RxSearch
import com.mvk.news.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import java.util.concurrent.TimeUnit

class MainViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    val homeIndiaNavigation = MutableLiveData<Event<Boolean>>()
    val homeUSNavigation = MutableLiveData<Event<Boolean>>()
    val searchQuery = MutableLiveData<Event<String>>()

    /**
     * Load the first tab from the bottom navigation bar
     */
    override fun onCreate() {
        homeIndiaNavigation.postValue(Event(true))
    }

    /**
     * Update live data when the country India is selected from the bottom navigation bar
     */
    fun onIndiaSelected() {
        homeIndiaNavigation.postValue(Event(true))
    }

    /**
     * Update live data when the country US is selected from the bottom navigation bar
     */
    fun onUSSelected() {
        homeUSNavigation.postValue(Event(true))
    }

    /**
     * Handle search from the app bar
     */
    fun handleSearch(categorySearchView: SearchView) {
        compositeDisposable.add(
            RxSearch.fromView(categorySearchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter { it.isNotEmpty() && it.length >= 2 }
                .map { it.toLowerCase(Locale.ROOT).trim() }
                .distinctUntilChanged()
                .observeOn(schedulerProvider.ui())
                .subscribe {
                    searchQuery.postValue(Event(it))
                }
        )
    }

}
