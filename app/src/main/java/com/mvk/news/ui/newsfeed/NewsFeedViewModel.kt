package com.mvk.news.ui.newsfeed

import androidx.lifecycle.MutableLiveData
import com.mvk.news.data.model.NewsArticles
import com.mvk.news.data.repository.NewsRepository
import com.mvk.news.ui.base.BaseViewModel
import com.mvk.news.utils.common.Constants
import com.mvk.news.utils.common.Resource
import com.mvk.news.utils.network.NetworkHelper
import com.mvk.news.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor

class NewsFeedViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val newsRepository: NewsRepository,
    private val allPostList: ArrayList<NewsArticles>,
    private val paginator: PublishProcessor<Int>
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    val loading = MutableLiveData<Boolean>()
    val posts = MutableLiveData<Resource<List<NewsArticles>>>()
    var pageId: Int = 1
    var category: String = Constants.DEFAULT_CATEGORY
    var api = Constants.API_HEADLINES
    var searchQuery = ""

    init {
        compositeDisposable.add(
            paginator
                .onBackpressureDrop()
                .doOnNext {
                    loading.postValue(true)
                }
                .concatMapSingle { pageId ->
                    if (api == Constants.API_HEADLINES) {
                        return@concatMapSingle newsRepository
                            .doNewsHeadlinesCall(country = "us", category = category, page = pageId)
                            .subscribeOn(schedulerProvider.io())
                            .doOnError { handleNetworkError(it) }
                    } else {
                        return@concatMapSingle newsRepository
                            .doSearchCall(searchQuery = searchQuery, page = pageId)
                            .subscribeOn(schedulerProvider.io())
                            .doOnError { handleNetworkError(it) }
                    }
                }
                .subscribe(
                    {
                        it.articles?.let { articles -> allPostList.addAll(articles) }
                        pageId = pageId.plus(1)
                        loading.postValue(false)
                        posts.postValue(Resource.success(it.articles))
                    },
                    {
                        handleNetworkError(it)
                    }
                )
        )
    }

    override fun onCreate() {
        loadMorePosts()
    }

    private fun loadMorePosts() {
        if (checkInternetConnectionWithMessage()) paginator.onNext(pageId)
    }

    fun onLoadMore() {
        if (loading.value != null && loading.value == false) loadMorePosts()
    }

    fun fetchHeadlinesWithCategory(category: String?) {
        category?.let {
            this.category = category
            pageId = 1
            api = Constants.API_HEADLINES
            paginator.onNext(pageId)
        }
    }

    fun fetchSearchQuery(query: String?) {
        query?.let {
            searchQuery = query
            pageId = 1
            api = Constants.API_SEARCH
            paginator.onNext(pageId)
        }
    }
}
