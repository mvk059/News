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
        setupSearchView()
        setupCategoryAdapter()
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.homeNavigation.observe(this, {
            it.getIfNotHandled()?.run {
                navigationController.showNewsFeedFragment(tag = TAG + homeTagParam, country = country)
            }
        })

        viewModel.searchQuery.observe(this, {
            it.getIfNotHandled()?.run {
                newsCategoryAdapter.clearSelection()
                navigationController.showNewsFeedFragment(tag = TAG + homeTagParam, query = this)
            }
        })

        viewModel.categoryList.observe(this, {
            it.data?.run {
//                dataBinding.categorySearchView.setQuery("", false)
                newsCategoryAdapter.appendData(this)
            }
        })
    }

    private fun setupSearchView() {
//        viewModel.handleSearch(dataBinding.categorySearchView)
    }

    private fun setupCategoryAdapter() {
        dataBinding.categoryRV.apply {
            layoutManager = linearLayoutManager
            adapter = newsCategoryAdapter
        }
        viewModel.getNewsCategoryList()
    }

    fun clearCategorySelection() {
        newsCategoryAdapter.clearSelection()
    }
}