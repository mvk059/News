package com.mvk.news.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.mvk.news.data.model.NewsArticles
import com.mvk.news.data.model.NewsCategory
import com.mvk.news.data.repository.NewsCategoryRepository
import com.mvk.news.data.repository.NewsRepository
import com.mvk.news.ui.base.BaseViewModel
import com.mvk.news.utils.common.Event
import com.mvk.news.utils.common.Resource
import com.mvk.news.utils.network.NetworkHelper
import com.mvk.news.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        newsRepository: NewsRepository,
        private val newsCategoryRepository: NewsCategoryRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    val homeNavigation = MutableLiveData<Event<Boolean>>()
    val categoryList = MutableLiveData<Resource<List<NewsCategory>>>()

    override fun onCreate() {
    }

    fun loadNewsFeed() {
        homeNavigation.postValue(Event(true))
    }

    fun getNewsCategoryList() {
        val list = newsCategoryRepository.getCategoryList()
        categoryList.postValue(Resource.success(list))
    }

}
