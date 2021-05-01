package com.mvk.news.di.module

import androidx.lifecycle.ViewModelProvider
import com.mvk.news.data.repository.NewsRepository
import com.mvk.news.ui.base.BaseFragment
import com.mvk.news.ui.newsfeed.NewsFeedViewModel
import com.mvk.news.utils.ViewModelProviderFactory
import com.mvk.news.utils.network.NetworkHelper
import com.mvk.news.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor

@Module
class FragmentModule(private val fragment: BaseFragment<*, *>) {

    @Provides
    fun provideNewsFeedViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        newsRepository: NewsRepository,
    ): NewsFeedViewModel {
        val viewModelFactory = ViewModelProviderFactory(NewsFeedViewModel::class) {
            NewsFeedViewModel(
                schedulerProvider,
                compositeDisposable,
                networkHelper,
                newsRepository,
                PublishProcessor.create()
            )
        }
        return ViewModelProvider(fragment, viewModelFactory).get(NewsFeedViewModel::class.java)
    }
}