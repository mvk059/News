package com.mvk.news.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import com.mvk.news.BR
import com.mvk.news.R
import com.mvk.news.databinding.ActivityMainBinding
import com.mvk.news.di.component.ActivityComponent
import com.mvk.news.ui.base.BaseActivity
import com.mvk.news.utils.navigation.NavigationController
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    @Inject
    lateinit var mainSharedViewModel: MainViewModel

    @Inject
    lateinit var navigationController: NavigationController

    override fun provideDataBindingVariable(): Int = BR.mainVM

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun injectDependencies(activityComponent: ActivityComponent) =
        activityComponent.inject(this)

    override fun setupView(savedInstanceState: Bundle?) {
        viewModel.loadNewsFeed()
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.homeNavigation.observe(this, Observer {
            it.getIfNotHandled()?.run {
                navigationController.showNewsFeedFragment()
            }
        })

    }
}