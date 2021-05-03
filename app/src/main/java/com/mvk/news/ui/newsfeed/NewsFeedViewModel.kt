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
    var category: String = Constants.DEFAULT_CATEGORY
    private var pageId: Int = 1
    private var apiToCall = Constants.API_HEADLINES
    private var searchQuery = ""
    lateinit var country: String

    /**
     * Setup the api
     */
    init {
        compositeDisposable.add(
            paginator
                .onBackpressureDrop()
                .doOnNext {
                    loading.postValue(true)
                }
                .concatMapSingle { pageId ->
                    // Call the headlines api
                    if (apiToCall == Constants.API_HEADLINES) {
                        return@concatMapSingle newsRepository
                            .doNewsHeadlinesCall(country = country, category = category, page = pageId)
                            .subscribeOn(schedulerProvider.io())
                            .doOnError {
                                loading.postValue(false)
                                handleNetworkError(it)
                            }
                    } else {
                        // Call the everything api
                        return@concatMapSingle newsRepository
                            .doSearchCall(searchQuery = searchQuery, page = pageId)
                            .subscribeOn(schedulerProvider.io())
                            .doOnError {
                                loading.postValue(false)
                                handleNetworkError(it)
                            }
                    }
                }
                .subscribe(
                    {
                        // Handle success
                        it.articles?.let { articles -> allPostList.addAll(articles) }
                        pageId = pageId.plus(1)
                        loading.postValue(false)
                        posts.postValue(Resource.success(it.articles))
                    },
                    {
                        // Handle failure
                        loading.postValue(false)
                        handleNetworkError(it)
                    }
                )
        )
    }

    override fun onCreate() { /* Empty */ }

    /**
     * Load posts
     */
    fun loadMorePosts() {
        if (checkInternetConnectionWithMessage()) paginator.onNext(pageId)
    }

    /**
     * Load more posts on pagination
     */
    fun onLoadMore() {
        if (loading.value != null && loading.value == false) loadMorePosts()
    }

    /**
     * Fetch the headlines for the updated category
     *
     * @param category Category
     */
    fun fetchHeadlinesWithCategory(category: String?) {
        category?.let {
            this.category = category
            pageId = 1
            apiToCall = Constants.API_HEADLINES
            paginator.onNext(pageId)
        }
    }

    /**
     * Fetch the news for the search query
     *
     * @param query Query
     */
    fun fetchSearchQuery(query: String?) {
        query?.let {
            searchQuery = query
            pageId = 1
            apiToCall = Constants.API_SEARCH
            paginator.onNext(pageId)
        }
    }
}
