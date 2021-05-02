package com.mvk.news.ui.home.adapter

import android.view.View
import android.view.ViewGroup
import com.mvk.news.R
import com.mvk.news.data.model.NewsCategory
import com.mvk.news.di.component.ViewHolderComponent
import com.mvk.news.ui.base.BaseItemViewHolder
import com.mvk.news.ui.home.HomeFragment
import com.mvk.news.utils.navigation.NavigationController
import kotlinx.android.synthetic.main.item_view_news_category.view.*
import javax.inject.Inject

class NewsCategoryItemViewHolder(parent: ViewGroup) :
    BaseItemViewHolder<NewsCategory, NewsCategoryItemViewModel>(R.layout.item_view_news_category, parent) {

    @Inject
    lateinit var navigationController: NavigationController

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

    fun refreshNewsFeed() {
        navigationController.showNewsFeedFragment(
            tag = HomeFragment.TAG + HomeFragment.homeTagParam,
            category = viewModel.data.value?.category.toString()
        )
    }
}