package com.mvk.news.di.module

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvk.news.data.repository.NewsRepository
import com.mvk.news.ui.base.BaseFragment
import com.mvk.news.ui.newsfeed.NewsFeedViewModel
import com.mvk.news.ui.newsfeed.adapter.NewsFeedAdapter
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
    fun provideLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(fragment.context)

    @Provides
    fun provideNewsFeedAdapter() = NewsFeedAdapter(fragment.lifecycle, ArrayList())

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
                ArrayList(),
                PublishProcessor.create()
            )
        }
        return ViewModelProvider(fragment, viewModelFactory).get(NewsFeedViewModel::class.java)
    }
}