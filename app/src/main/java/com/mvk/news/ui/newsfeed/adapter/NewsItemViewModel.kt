package com.mvk.news.ui.newsfeed.adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mvk.news.data.model.NewsArticles
import com.mvk.news.data.repository.NewsRepository
import com.mvk.news.ui.base.BaseItemViewModel
import com.mvk.news.utils.common.TimeUtils
import com.mvk.news.utils.network.NetworkHelper
import com.mvk.news.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewsItemViewModel @Inject constructor(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper
) : BaseItemViewModel<NewsArticles>(schedulerProvider, compositeDisposable, networkHelper) {

    val source: LiveData<String> = Transformations.map(data) { it?.source?.name }
    val author: LiveData<String> = Transformations.map(data) { it?.author }
    val publishedAt: LiveData<String> = Transformations.map(data) { it?.publishedAt }
    val title: LiveData<String> = Transformations.map(data) { it?.title }
    val imageURL: LiveData<String> = Transformations.map(data) { it?.urlToImage }

    override fun onCreate() { /* Empty */ }

    fun onNewsFeedItemClick(): String? {
        data.value?.url?.let {
            if (networkHelper.isNetworkConnected()) {
                return it
            }
        } ?: return null
        return null
    }

    fun getFormattedTime(time: String): String? = TimeUtils.formatTime(time)

}