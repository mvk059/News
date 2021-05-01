package com.mvk.news.di.module

import android.app.Application
import android.content.Context
import com.mvk.instagram.di.ApplicationContext
import com.mvk.news.BuildConfig
import com.mvk.news.NewsApp
import com.mvk.news.data.remote.NetworkService
import com.mvk.news.data.remote.Networking
import com.mvk.news.utils.network.NetworkHelper
import com.mvk.news.utils.rx.RxSchedulerProvider
import com.mvk.news.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: NewsApp) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(): Context = application

    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService =
        Networking.create(
            BuildConfig.API_KEY,
            BuildConfig.BASE_URL
        )

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Singleton
    @Provides
    fun provideNetworkHelper(): NetworkHelper = NetworkHelper(application)
}