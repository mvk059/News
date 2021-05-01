package com.mvk.news.ui.newsfeed.adapter

import com.mvk.news.data.model.NewsArticles
import com.mvk.news.data.repository.NewsRepository
import com.mvk.news.ui.base.BaseItemViewModel
import com.mvk.news.utils.network.NetworkHelper
import com.mvk.news.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewsItemViewModel @Inject constructor(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    newsRepository: NewsRepository,
) : BaseItemViewModel<NewsArticles>(schedulerProvider, compositeDisposable, networkHelper) {

    override fun onCreate() {

    }
}