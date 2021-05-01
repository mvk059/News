package com.mvk.news.di.component

import com.mvk.instagram.di.ViewModelScope
import com.mvk.news.di.module.ViewHolderModule
import com.mvk.news.ui.newsfeed.adapter.NewsItemViewHolder
import dagger.Component

@ViewModelScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ViewHolderModule::class]
)
interface ViewHolderComponent {

    fun inject(viewHolder: NewsItemViewHolder)
}