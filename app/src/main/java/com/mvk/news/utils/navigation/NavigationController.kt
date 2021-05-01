package com.mvk.news.utils.navigation

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mvk.news.R
import com.mvk.news.ui.newsfeed.NewsFeedFragment
import javax.inject.Inject

class NavigationController @Inject constructor(var context: Context, var fragmentManager: FragmentManager) {

    fun showNewsFeedFragment(): Fragment {
        val fragmentTransaction = fragmentManager.beginTransaction()
        var fragment = fragmentManager.findFragmentByTag(NewsFeedFragment.TAG) as NewsFeedFragment?

        if (fragment == null) {
            fragment = NewsFeedFragment.newInstance()
            fragmentTransaction.add(R.id.containerFragment, fragment, NewsFeedFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }
        fragmentTransaction.commit()
        return fragment
    }
}