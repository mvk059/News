package com.mvk.news

import android.app.Application
import com.mvk.news.di.component.ApplicationComponent
import com.mvk.news.di.component.DaggerApplicationComponent
import com.mvk.news.di.module.ApplicationModule

class NewsApp : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }
}