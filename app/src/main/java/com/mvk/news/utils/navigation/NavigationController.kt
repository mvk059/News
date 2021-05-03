package com.mvk.news.utils.navigation

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mvk.news.R
import com.mvk.news.ui.home.HomeFragment
import com.mvk.news.ui.newsfeed.NewsFeedFragment
import javax.inject.Inject

/**
 * Handles the navigation in the app
 */
class NavigationController @Inject constructor(var context: Context, var fragmentManager: FragmentManager) {

    /**
     * Load the [HomeFragment]
     *
     * @param tag Custom tag for the fragment
     * @param country Selected country in the bottom navigation bar
     */
    fun showHomeFragment(tag: String, country: String): Fragment {
        val fragmentTransaction = fragmentManager.beginTransaction()

        var fragment = fragmentManager.findFragmentByTag(tag) as HomeFragment?
        if (fragment == null) {
            fragment = HomeFragment.newInstance(tag, country)
            fragmentTransaction.replace(R.id.homeContainerFragment, fragment, tag)
        } else {
            fragmentTransaction.show(fragment)
        }
        fragmentTransaction.commit()
        return fragment
    }

    /**
     * Load the [NewsFeedFragment]
     *
     * @param tag Custom tag for the fragment
     * @param category Selected category
     * @param query Search query
     * @param country Selected country in the bottom navigation bar
     */
    fun showNewsFeedFragment(
        tag: String,
        category: String? = "",
        query: String? = "",
        country: String? = ""
    ): Fragment {
        val fragmentTransaction = fragmentManager.beginTransaction()

        var fragment = fragmentManager.findFragmentByTag(tag) as NewsFeedFragment?

        if (fragment == null) {
            fragment = NewsFeedFragment.newInstance(country)
            fragmentTransaction.replace(R.id.newsFeedContainerFragment, fragment, tag)
        } else {
            fragmentTransaction.show(fragment)
            if (!category.isNullOrEmpty())
                fragment.updateCategoryHeadlines(category)
            if (!query.isNullOrEmpty())
                fragment.updateSearchQuery(query)
        }
        fragmentTransaction.commit()
        return fragment
    }
}