package com.mvk.news.ui.main

import androidx.lifecycle.MutableLiveData
import com.mvk.news.data.repository.NewsRepository
import com.mvk.news.ui.base.BaseViewModel
import com.mvk.news.utils.common.Event
import com.mvk.news.utils.log.Logger
import com.mvk.news.utils.network.NetworkHelper
import com.mvk.news.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    newsRepository: NewsRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    val homeNavigation = MutableLiveData<Event<Boolean>>()

    override fun onCreate() {

    }

    fun loadNewsFeed() {
        homeNavigation.postValue(Event(true))
    }

}
