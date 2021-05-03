package com.mvk.news.ui.home

import androidx.lifecycle.MutableLiveData
import com.mvk.news.data.model.NewsCategory
import com.mvk.news.data.repository.NewsCategoryRepository
import com.mvk.news.ui.base.BaseViewModel
import com.mvk.news.utils.common.Event
import com.mvk.news.utils.common.Resource
import com.mvk.news.utils.network.NetworkHelper
import com.mvk.news.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val newsCategoryRepository: NewsCategoryRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    val homeNavigation = MutableLiveData<Event<Boolean>>()
    val searchQuery = MutableLiveData<Event<String>>()
    val categoryList = MutableLiveData<Resource<List<NewsCategory>>>()

    override fun onCreate() { /* Empty */ }

    /**
     * Update live data of NewsFeedFragment
     */
    fun loadNewsFeed() {
        homeNavigation.postValue(Event(true))
    }

    /**
     * Get news category data
     */
    fun getNewsCategoryList() {
        val list = newsCategoryRepository.getCategoryList()
        categoryList.postValue(Resource.success(list))
    }
}