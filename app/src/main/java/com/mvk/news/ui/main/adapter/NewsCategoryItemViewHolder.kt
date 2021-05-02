package com.mvk.news.ui.main.adapter

import android.view.View
import android.view.ViewGroup
import com.mvk.news.R
import com.mvk.news.data.model.NewsCategory
import com.mvk.news.di.component.ViewHolderComponent
import com.mvk.news.ui.base.BaseItemViewHolder
import kotlinx.android.synthetic.main.item_view_news_category.view.*

class NewsCategoryItemViewHolder(parent: ViewGroup) :
        BaseItemViewHolder<NewsCategory, NewsCategoryItemViewModel>(R.layout.item_view_news_category, parent) {

    override fun injectDependencies(viewHolderComponent: ViewHolderComponent) {
        viewHolderComponent.inject(this)
    }

    override fun setupView(view: View) {

    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.categoryName.observe(this, {
            itemView.tvCategoryName.text = it
        })
    }
}