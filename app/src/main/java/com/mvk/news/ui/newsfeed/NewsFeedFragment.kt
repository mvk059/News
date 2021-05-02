package com.mvk.news.ui.newsfeed

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mvk.news.BR
import com.mvk.news.R
import com.mvk.news.databinding.FragmentNewsFeedBinding
import com.mvk.news.di.component.FragmentComponent
import com.mvk.news.ui.base.BaseFragment
import com.mvk.news.ui.home.HomeFragment
import com.mvk.news.ui.newsfeed.adapter.NewsFeedAdapter
import com.mvk.news.utils.common.Constants
import javax.inject.Inject

class NewsFeedFragment : BaseFragment<FragmentNewsFeedBinding, NewsFeedViewModel>() {

    companion object {

        const val TAG = "NewsFeedFragment"

        fun newInstance(): NewsFeedFragment {
            val args = Bundle()
            val fragment = NewsFeedFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var newsFeedAdapter: NewsFeedAdapter

    lateinit var searchView: SearchView

    override fun provideDataBindingVariable(): Int = BR.newsVM

    override fun provideLayoutId(): Int = R.layout.fragment_news_feed

    override fun injectDependencies(fragmentComponent: FragmentComponent) =
        fragmentComponent.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun setupView(view: View) {
        dataBinding.newsFeedRV.apply {
            layoutManager = linearLayoutManager
            adapter = newsFeedAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    layoutManager?.run {
                        if (this is LinearLayoutManager
                                && itemCount > 0
                                && itemCount == findLastVisibleItemPosition() + 1) {
                            viewModel.onLoadMore()
                        }
                    }
                }
            })
        }
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.loading.observe(this, {
            dataBinding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.posts.observe(this, {
            it.data?.run {
                newsFeedAdapter.appendData(this)
            }
        })
    }

    fun getCategoryHeadlines(category: String?) {
        newsFeedAdapter.clearList()
        searchView.setQuery("", false)
        searchView.onActionViewCollapsed()
        searchView.isIconified = true
        viewModel.fetchHeadlinesWithCategory(category)
    }

    fun getSearchQuery(query: String?) {
        clear()
        viewModel.fetchSearchQuery(query)
    }

    private fun clear() {
        newsFeedAdapter.clearList()
        var tag = this.tag
        tag = tag?.replace(HomeFragment.TAG, "")
        val homeFragment = activity?.supportFragmentManager?.findFragmentByTag(tag) as HomeFragment
        homeFragment.let {
            homeFragment.clearCategorySelection()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val search = menu.findItem(R.id.menu_search)
        searchView = search?.actionView as SearchView
    }
}