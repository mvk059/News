package com.mvk.news.ui.main

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvk.news.BR
import com.mvk.news.R
import com.mvk.news.databinding.ActivityMainBinding
import com.mvk.news.di.component.ActivityComponent
import com.mvk.news.ui.base.BaseActivity
import com.mvk.news.ui.home.adapter.NewsCategoryAdapter
import com.mvk.news.utils.navigation.NavigationController
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    @Inject
    lateinit var navigationController: NavigationController

//    @Inject
//    lateinit var linearLayoutManager: LinearLayoutManager

//    @Inject
//    lateinit var newsCategoryAdapter: NewsCategoryAdapter

    override fun provideDataBindingVariable(): Int = BR.mainVM

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun injectDependencies(activityComponent: ActivityComponent) =
        activityComponent.inject(this)

    override fun setupView(savedInstanceState: Bundle?) {
        viewModel.loadHomeFragment()
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.homeNavigation.observe(this, {
            it.getIfNotHandled()?.run {
                navigationController.showHomeFragment()
            }
        })

    }
}