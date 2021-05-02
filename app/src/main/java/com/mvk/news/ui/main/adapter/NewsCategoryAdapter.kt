package com.mvk.news.ui.main.adapter

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.mvk.news.data.model.NewsCategory
import com.mvk.news.ui.base.BaseAdapter

class NewsCategoryAdapter(
        parentLifecycle: Lifecycle,
        posts: ArrayList<NewsCategory>
) : BaseAdapter<NewsCategory, NewsCategoryItemViewHolder>(parentLifecycle, posts) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsCategoryItemViewHolder(parent)
}