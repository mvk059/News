package com.mvk.news.ui.newsfeed

import androidx.lifecycle.MutableLiveData
import com.mvk.news.data.model.NewsArticles
import com.mvk.news.data.repository.NewsRepository
import com.mvk.news.ui.base.BaseViewModel
import com.mvk.news.utils.common.Resource
import com.mvk.news.utils.log.Logger
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
    private val paginator: PublishProcessor<Pair<String?, String?>>
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    val loading = MutableLiveData<Boolean>()
    val posts = MutableLiveData<Resource<List<NewsArticles>>>()

    override fun onCreate() {
        loading.postValue(true)
        compositeDisposable.addAll(
            newsRepository.doNewsHeadlinesCall(country = "us", category = "business", page = 1)
                .subscribeOn(schedulerProvider.io())
                .subscribe(
                    {
                        it.articles?.let { articles -> allPostList.addAll(articles) }
                        loading.postValue(false)
                        posts.postValue(Resource.success(it.articles))
                    },
                    {
                        handleNetworkError(it)
                    }
                )
        )
    }

}