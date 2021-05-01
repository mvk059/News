package com.mvk.news.ui.newsfeed.adapter

import android.view.View
import android.view.ViewGroup
import com.mvk.news.R
import com.mvk.news.data.model.NewsArticles
import com.mvk.news.di.component.ViewHolderComponent
import com.mvk.news.ui.base.BaseItemViewHolder

class NewsItemViewHolder(parent: ViewGroup) :
    BaseItemViewHolder<NewsArticles, NewsItemViewModel>(R.layout.item_view_posts, parent) {

    override fun injectDependencies(viewHolderComponent: ViewHolderComponent) {
        viewHolderComponent.inject(this)
    }

    override fun setupView(view: View) {

    }

    override fun setupObservers() {
        super.setupObservers()
    }
}