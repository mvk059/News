package com.mvk.news.di.module

import android.app.Application
import android.content.Context
import com.mvk.instagram.di.ApplicationContext
import com.mvk.news.BuildConfig
import com.mvk.news.NewsApp
import com.mvk.news.data.remote.NetworkService
import com.mvk.news.data.remote.Networking
import dagger.Module
import dagger.Provides
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
}