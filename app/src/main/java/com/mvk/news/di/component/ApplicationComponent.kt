package com.mvk.news.di.component

import android.app.Application
import android.content.Context
import com.mvk.instagram.di.ApplicationContext
import com.mvk.news.NewsApp
import com.mvk.news.data.remote.NetworkService
import com.mvk.news.data.repository.NewsRepository
import com.mvk.news.di.module.ApplicationModule
import com.mvk.news.utils.network.NetworkHelper
import com.mvk.news.utils.rx.SchedulerProvider
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(app: NewsApp)

    fun getApplication(): Application

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService(): NetworkService

    fun getNetworkHelper(): NetworkHelper

    fun getNetworkRepository(): NewsRepository

    fun getSchedulerProvider(): SchedulerProvider

    fun getCompositeDisposable(): CompositeDisposable
}