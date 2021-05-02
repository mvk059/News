package com.mvk.news.ui.main

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import com.mvk.news.data.repository.NewsCategoryRepository
import com.mvk.news.data.repository.NewsRepository
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
        networkHelper: NetworkHelper,
        newsRepository: NewsRepository,
        private val newsCategoryRepository: NewsCategoryRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    val homeIndiaNavigation = MutableLiveData<Event<Boolean>>()
    val homeUSNavigation = MutableLiveData<Event<Boolean>>()
    val searchQuery = MutableLiveData<Event<String>>()

    override fun onCreate() {
        homeIndiaNavigation.postValue(Event(true))
    }

    fun onIndiaSelected() {
        homeIndiaNavigation.postValue(Event(true))
    }

    fun onUSSelected() {
        homeUSNavigation.postValue(Event(true))
    }

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
