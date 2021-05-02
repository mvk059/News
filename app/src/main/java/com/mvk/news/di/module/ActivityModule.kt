package com.mvk.news.di.module

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvk.news.data.repository.NewsCategoryRepository
import com.mvk.news.data.repository.NewsRepository
import com.mvk.news.ui.base.BaseActivity
import com.mvk.news.ui.main.MainViewModel
import com.mvk.news.ui.main.adapter.NewsCategoryAdapter
import com.mvk.news.utils.ViewModelProviderFactory
import com.mvk.news.utils.navigation.NavigationController
import com.mvk.news.utils.network.NetworkHelper
import com.mvk.news.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule(private val activity: BaseActivity<*, *>) {

    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

    @Provides
    fun provideNewsFeedAdapter() = NewsCategoryAdapter(activity.lifecycle, ArrayList())

    @Provides
    fun provideNavigationController(): NavigationController =
            NavigationController(activity, activity.supportFragmentManager)

    @Provides
    fun provideMainViewModel(
            schedulerProvider: SchedulerProvider,
            compositeDisposable: CompositeDisposable,
            networkHelper: NetworkHelper,
            newsRepository: NewsRepository,
            newsCategoryRepository: NewsCategoryRepository,
    ): MainViewModel {
        val viewModelFactory = ViewModelProviderFactory(MainViewModel::class) {
            MainViewModel(schedulerProvider, compositeDisposable, networkHelper, newsRepository, newsCategoryRepository)
        }
        return ViewModelProvider(activity, viewModelFactory).get(MainViewModel::class.java)
    }
}