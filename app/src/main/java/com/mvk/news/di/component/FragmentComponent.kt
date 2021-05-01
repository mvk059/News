package com.mvk.news.di.component

import com.mvk.instagram.di.FragmentScope
import com.mvk.news.di.module.FragmentModule
import com.mvk.news.ui.newsfeed.NewsFeedFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [FragmentModule::class]
)
interface FragmentComponent {

    fun inject(fragment: NewsFeedFragment)

}