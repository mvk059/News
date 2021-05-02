package com.mvk.news.ui.main

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.mvk.news.BR
import com.mvk.news.R
import com.mvk.news.databinding.ActivityMainBinding
import com.mvk.news.di.component.ActivityComponent
import com.mvk.news.ui.base.BaseActivity
import com.mvk.news.ui.home.HomeFragment
import com.mvk.news.utils.common.Constants
import com.mvk.news.utils.navigation.NavigationController
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    @Inject
    lateinit var navigationController: NavigationController

    lateinit var searchView: SearchView

    override fun provideDataBindingVariable(): Int = BR.mainVM

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun injectDependencies(activityComponent: ActivityComponent) =
        activityComponent.inject(this)

    override fun setupView(savedInstanceState: Bundle?) {
        dataBinding.bottomNavigation.selectedItemId = R.id.itemIndia

        dataBinding.bottomNavigation.run {
            itemIconTintList = null
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.itemIndia -> {
                        viewModel.onIndiaSelected()
                        true
                    }
                    R.id.itemUS -> {
                        viewModel.onUSSelected()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.homeIndiaNavigation.observe(this, {
            it.getIfNotHandled()?.run {
                this@MainActivity.invalidateOptionsMenu()
                navigationController.showHomeFragment(Constants.TAG_HOME_INDIA)
            }
        })

        viewModel.homeUSNavigation.observe(this, {
            it.getIfNotHandled()?.run {
                searchView.isIconified = true
                this@MainActivity.invalidateOptionsMenu()
                navigationController.showHomeFragment(Constants.TAG_HOME_US)
            }
        })

        viewModel.searchQuery.observe(this, {
            it.getIfNotHandled()?.run {
                navigationController.showNewsFeedFragment(
                    tag = HomeFragment.TAG + HomeFragment.homeTagParam,
                    query = this
                )
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        val search = menu?.findItem(R.id.menu_search)
        searchView = search?.actionView as SearchView
        viewModel.handleSearch(searchView)
        return true
    }
}