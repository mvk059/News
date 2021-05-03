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

    private lateinit var searchView: SearchView

    override fun provideDataBindingVariable(): Int = BR.mainVM

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun injectDependencies(activityComponent: ActivityComponent) =
        activityComponent.inject(this)

    override fun setupView(savedInstanceState: Bundle?) {
        // Bottom navigation default selected item
        dataBinding.bottomNavigation.selectedItemId = R.id.itemIndia

        // Bottom navigation click listener
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

        // Load the HomeFragment passing India as the selected country
        viewModel.homeIndiaNavigation.observe(this, {
            it.getIfNotHandled()?.run {
                this@MainActivity.invalidateOptionsMenu()
                navigationController.showHomeFragment(Constants.TAG_HOME_INDIA, Constants.COUNTRY_INDIA)
            }
        })

        // Load the HomeFragment passing US as the selected country
        viewModel.homeUSNavigation.observe(this, {
            it.getIfNotHandled()?.run {
                searchView.isIconified = true
                this@MainActivity.invalidateOptionsMenu()
                navigationController.showHomeFragment(Constants.TAG_HOME_US, Constants.COUNTRY_US)
            }
        })

        // Load the NewsFeedFragment passing the search query
        viewModel.searchQuery.observe(this, {
            it.getIfNotHandled()?.run {
                navigationController.showNewsFeedFragment(
                    tag = HomeFragment.TAG + HomeFragment.homeTagParam,
                    query = this
                )
            }
        })
    }

    /**
     * Create the options menu
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        val search = menu?.findItem(R.id.menu_search)
        searchView = search?.actionView as SearchView
        viewModel.handleSearch(searchView)
        return true
    }
}