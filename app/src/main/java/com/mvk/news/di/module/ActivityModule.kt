package com.mvk.news.di.module

import androidx.lifecycle.ViewModelProvider
import com.mvk.news.data.repository.NewsRepository
import com.mvk.news.ui.main.MainViewModel
import com.mvk.news.ui.base.BaseActivity
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
    fun provideNavigationController(): NavigationController =
        NavigationController(activity, activity.supportFragmentManager)

    @Provides
    fun provideMainViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        newsRepository: NewsRepository
    ): MainViewModel {
        val viewModelFactory = ViewModelProviderFactory(MainViewModel::class) {
            MainViewModel(schedulerProvider, compositeDisposable, networkHelper, newsRepository)
        }
        return ViewModelProvider(activity, viewModelFactory).get(MainViewModel::class.java)
    }
}