package com.mvk.news.ui.newsfeed

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mvk.news.BR
import com.mvk.news.R
import com.mvk.news.databinding.FragmentNewsFeedBinding
import com.mvk.news.di.component.FragmentComponent
import com.mvk.news.ui.base.BaseFragment
import com.mvk.news.ui.newsfeed.adapter.NewsFeedAdapter
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

    override fun provideDataBindingVariable(): Int = BR.homeVM

    override fun provideLayoutId(): Int = R.layout.fragment_news_feed

    override fun injectDependencies(fragmentComponent: FragmentComponent) =
        fragmentComponent.inject(this)

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
}