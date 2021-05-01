package com.mvk.news.ui.newsfeed.adapter

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.mvk.news.data.model.NewsArticles
import com.mvk.news.ui.base.BaseAdapter

class NewsFeedAdapter(
    parentLifecycle: Lifecycle,
    posts: ArrayList<NewsArticles>
) : BaseAdapter<NewsArticles, NewsItemViewHolder>(parentLifecycle, posts) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsItemViewHolder(parent)
}