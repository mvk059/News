package com.mvk.news.ui.home

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import com.mvk.news.data.model.NewsCategory
import com.mvk.news.data.repository.NewsCategoryRepository
import com.mvk.news.data.repository.NewsRepository
import com.mvk.news.ui.base.BaseViewModel
import com.mvk.news.utils.common.Event
import com.mvk.news.utils.common.Resource
import com.mvk.news.utils.log.Logger
import com.mvk.news.utils.network.NetworkHelper
import com.mvk.news.utils.rx.RxSearch
import com.mvk.news.utils.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import java.util.concurrent.TimeUnit

class HomeViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val newsRepository: NewsRepository,
    private val newsCategoryRepository: NewsCategoryRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    val homeNavigation = MutableLiveData<Event<Boolean>>()
    val searchQuery = MutableLiveData<Event<String>>()
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