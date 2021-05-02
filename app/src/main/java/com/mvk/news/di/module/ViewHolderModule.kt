package com.mvk.news.di.module

import androidx.lifecycle.LifecycleRegistry
import com.mvk.instagram.di.ViewModelScope
import com.mvk.news.ui.base.BaseActivity
import com.mvk.news.ui.base.BaseItemViewHolder
import dagger.Module
import dagger.Provides

@Module
class ViewHolderModule(private val viewHolder: BaseItemViewHolder<*, *>) {

    @Provides
    @ViewModelScope
    fun provideLifecycleRegistry(): LifecycleRegistry = LifecycleRegistry(viewHolder)

    @Provides
    @ViewModelScope
    fun provideContext() = viewHolder.itemView.context

    @Provides
    @ViewModelScope
    fun provideFragmentManager() = (viewHolder.itemView.context as BaseActivity<*, *>).supportFragmentManager
}