package com.mvk.news.utils.navigation

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mvk.news.R
import com.mvk.news.ui.home.HomeFragment
import com.mvk.news.ui.newsfeed.NewsFeedFragment
import javax.inject.Inject

class NavigationController @Inject constructor(var context: Context, var fragmentManager: FragmentManager) {

    fun showHomeFragment(tag: String): Fragment {
        val fragmentTransaction = fragmentManager.beginTransaction()

        var fragment = fragmentManager.findFragmentByTag(tag) as HomeFragment?
        if (fragment == null) {
            fragment = HomeFragment.newInstance(tag)
            fragmentTransaction.replace(R.id.homeContainerFragment, fragment, tag)
        } else {
            fragmentTransaction.show(fragment)
        }
        fragmentTransaction.commit()
        return fragment
    }

    fun showNewsFeedFragment(
        tag: String,
        category: String? = "",
        query: String? = ""
    ): Fragment {
        val fragmentTransaction = fragmentManager.beginTransaction()

        var fragment = fragmentManager.findFragmentByTag(tag) as NewsFeedFragment?

        if (fragment == null) {
            fragment = NewsFeedFragment.newInstance()
            fragmentTransaction.replace(R.id.newsFeedContainerFragment, fragment, tag)
        } else {
            fragmentTransaction.show(fragment)
            if (!category.isNullOrEmpty())
                fragment.getCategoryHeadlines(category)
            if (!query.isNullOrEmpty())
                fragment.getSearchQuery(query)
        }
        fragmentTransaction.commit()
        return fragment
    }
}