package com.mvk.news.di.module

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvk.news.data.repository.NewsCategoryRepository
import com.mvk.news.data.repository.NewsRepository
import com.mvk.news.di.HorizontalRV
import com.mvk.news.ui.base.BaseFragment
import com.mvk.news.ui.home.HomeViewModel
import com.mvk.news.ui.home.adapter.NewsCategoryAdapter
import com.mvk.news.ui.newsfeed.NewsFeedViewModel
import com.mvk.news.ui.newsfeed.adapter.NewsFeedAdapter
import com.mvk.news.utils.ViewModelProviderFactory
import com.mvk.news.utils.navigation.NavigationController
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
    @HorizontalRV
    fun provideHorizontalLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(fragment.context, LinearLayoutManager.HORIZONTAL, false)

    @Provides
    fun provideNewsFeedAdapter() = NewsFeedAdapter(fragment.lifecycle, ArrayList())

    @Provides
    fun provideNewsCategoryAdapter() = NewsCategoryAdapter(fragment.lifecycle, ArrayList())

    @Provides
    fun provideNavigationController(): NavigationController =
        NavigationController(fragment.requireContext(), fragment.requireActivity().supportFragmentManager)

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

    @Provides
    fun provideHomeViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        newsCategoryRepository: NewsCategoryRepository
    ): HomeViewModel {
        val viewModelFactory = ViewModelProviderFactory(HomeViewModel::class) {
            HomeViewModel(schedulerProvider, compositeDisposable, networkHelper, newsCategoryRepository)
        }
        return ViewModelProvider(fragment, viewModelFactory).get(HomeViewModel::class.java)
    }
}