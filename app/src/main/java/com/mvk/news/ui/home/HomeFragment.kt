package com.mvk.news.ui.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvk.news.BR
import com.mvk.news.R
import com.mvk.news.databinding.FragmentHomeBinding
import com.mvk.news.di.HorizontalRV
import com.mvk.news.di.component.FragmentComponent
import com.mvk.news.ui.base.BaseFragment
import com.mvk.news.ui.home.adapter.NewsCategoryAdapter
import com.mvk.news.utils.common.Constants
import com.mvk.news.utils.navigation.NavigationController
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    companion object {
        const val TAG = "HomeFragment"
        var homeTagParam: String? = null

        fun newInstance(tag: String, country: String): HomeFragment =
             HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.HOME_PARAM_ARG, tag)
                    putString(Constants.HOME_COUNTRY_PARAM_ARG, country)
                }
            }
    }

    @Inject
    lateinit var navigationController: NavigationController

    @Inject
    @HorizontalRV
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var newsCategoryAdapter: NewsCategoryAdapter

    lateinit var country: String

    override fun provideDataBindingVariable(): Int = BR.homeVM

    override fun provideLayoutId(): Int = R.layout.fragment_home

    override fun injectDependencies(fragmentComponent: FragmentComponent) =
        fragmentComponent.inject(this)

    override fun setupView(view: View) {
        arguments?.let {
            homeTagParam = it.getString(Constants.HOME_PARAM_ARG)
            country = it.getString(Constants.HOME_COUNTRY_PARAM_ARG).toString()
        }
        viewModel.loadNewsFeed()
        setupCategoryAdapter()
    }

    override fun setupObservers() {
        super.setupObservers()

        // Load the NewsFeedFragment
        viewModel.homeNavigation.observe(this, {
            it.getIfNotHandled()?.run {
                navigationController.showNewsFeedFragment(tag = TAG + homeTagParam, country = country)
            }
        })

        // Load the NewsFeedFragment with the search query
        viewModel.searchQuery.observe(this, {
            it.getIfNotHandled()?.run {
                newsCategoryAdapter.clearSelection()
                navigationController.showNewsFeedFragment(tag = TAG + homeTagParam, query = this)
            }
        })

        // Update the category list
        viewModel.categoryList.observe(this, {
            it.data?.run {
                newsCategoryAdapter.appendData(this)
            }
        })
    }

    /**
     * Setup the category adapter
     */
    private fun setupCategoryAdapter() {
        dataBinding.categoryRV.apply {
            layoutManager = linearLayoutManager
            adapter = newsCategoryAdapter
        }
        viewModel.getNewsCategoryList()
    }

    /**
     * Clear the news category selection
     */
    fun clearCategorySelection() {
        newsCategoryAdapter.clearSelection()
    }
}