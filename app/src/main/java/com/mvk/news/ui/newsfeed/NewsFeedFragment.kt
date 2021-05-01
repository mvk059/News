package com.mvk.news.ui.newsfeed

import android.os.Bundle
import android.view.View
import com.mvk.news.BR
import com.mvk.news.R
import com.mvk.news.databinding.FragmentNewsFeedBinding
import com.mvk.news.di.component.FragmentComponent
import com.mvk.news.ui.base.BaseFragment

class NewsFeedFragment : BaseFragment<FragmentNewsFeedBinding, NewsFeedViewModel>() {

    companion object {

        const val TAG = "HomeFragment"

        fun newInstance(): NewsFeedFragment {
            val args = Bundle()
            val fragment = NewsFeedFragment()
            fragment.arguments = args
            return fragment
        }

    }

    override fun provideDataBindingVariable(): Int = BR.homeVM

    override fun provideLayoutId(): Int = R.layout.fragment_news_feed

    override fun injectDependencies(fragmentComponent: FragmentComponent) =
        fragmentComponent.inject(this)

    override fun setupView(view: View) {
    }

    override fun setupObservers() {
        super.setupObservers()
    }
}