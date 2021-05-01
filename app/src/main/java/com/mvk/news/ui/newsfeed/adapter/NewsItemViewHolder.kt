package com.mvk.news.ui.newsfeed.adapter

import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mvk.news.R
import com.mvk.news.data.model.NewsArticles
import com.mvk.news.di.component.ViewHolderComponent
import com.mvk.news.ui.base.BaseItemViewHolder
import com.mvk.news.utils.common.GlideHelper
import com.mvk.news.utils.log.Logger
import kotlinx.android.synthetic.main.item_view_posts.view.*

class NewsItemViewHolder(parent: ViewGroup) :
    BaseItemViewHolder<NewsArticles, NewsItemViewModel>(R.layout.item_view_posts, parent) {

    override fun injectDependencies(viewHolderComponent: ViewHolderComponent) {
        viewHolderComponent.inject(this)
    }

    override fun setupView(view: View) {
        itemView.newsFeedItem.setOnClickListener {
            // TODO Open browser
        }
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.source.observe(this, {
            itemView.tvSource.text = it
        })

        viewModel.publishedAt.observe(this, {
            itemView.tvTime.text = it
        })

        viewModel.author.observe(this, {
            itemView.tvAuthor.text =
                String.format(
                    itemView.tvAuthor.context.getString(R.string.news_feed_author_label, it)
                )
        })

        viewModel.imageURL.observe(this, {
            val glideRequest =
                Glide
                    .with(itemView.ivImage.context)
                    .load(GlideHelper.getProtectedUrl(it))
                    .centerCrop()
            glideRequest.into(itemView.ivImage)
        })

        viewModel.title.observe(this, {
            itemView.tvTitle.text = it
        })
    }
}