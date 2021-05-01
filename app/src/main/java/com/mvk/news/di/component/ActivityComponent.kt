package com.mvk.news.di.component

import com.mvk.instagram.di.ActivityScope
import com.mvk.news.di.module.ActivityModule
import com.mvk.news.ui.MainActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent {

    fun inject(activity: MainActivity)
}