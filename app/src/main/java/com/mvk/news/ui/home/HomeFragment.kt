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
import com.mvk.news.utils.navigation.NavigationController
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    companion object {
        const val TAG = "HomeFragment"

        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var navigationController: NavigationController

    @Inject
    @HorizontalRV
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var newsCategoryAdapter: NewsCategoryAdapter

    override fun provideDataBindingVariable(): Int = BR.homeVM

    override fun provideLayoutId(): Int = R.layout.fragment_home

    override fun injectDependencies(fragmentComponent: FragmentComponent) =
        fragmentComponent.inject(this)

    override fun setupView(view: View) {
        viewModel.loadNewsFeed()
        setupCategoryAdapter()
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.homeNavigation.observe(this, {
            it.getIfNotHandled()?.run {
                navigationController.showNewsFeedFragment()
            }
        })

        viewModel.categoryList.observe(this, {
            it.data?.run {
                newsCategoryAdapter.appendData(this)
            }
        })
    }

    private fun setupCategoryAdapter() {
        dataBinding.categoryRV.apply {
            layoutManager = linearLayoutManager
            adapter = newsCategoryAdapter
        }
        viewModel.getNewsCategoryList()
    }
}