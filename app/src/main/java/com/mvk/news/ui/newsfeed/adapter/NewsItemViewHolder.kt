package com.mvk.news.ui.newsfeed.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.mvk.news.R
import com.mvk.news.data.model.NewsArticles
import com.mvk.news.di.component.ViewHolderComponent
import com.mvk.news.ui.base.BaseItemViewHolder
import com.mvk.news.utils.common.GlideHelper
import com.mvk.news.utils.common.Resource
import kotlinx.android.synthetic.main.item_view_posts.view.*
import java.text.SimpleDateFormat
import java.util.*


class NewsItemViewHolder(parent: ViewGroup) :
        BaseItemViewHolder<NewsArticles, NewsItemViewModel>(R.layout.item_view_posts, parent) {

    override fun injectDependencies(viewHolderComponent: ViewHolderComponent) {
        viewHolderComponent.inject(this)
    }

    override fun setupView(view: View) {
        itemView.newsFeedItem.setOnClickListener {
            val url = viewModel.onNewsFeedItemClick()
            url?.let {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                itemView.newsFeedItem.context.startActivity(browserIntent)
            } ?: viewModel.messageStringId.postValue(Resource.error(R.string.news_feed_url_not_available))
        }
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.source.observe(this, {
            itemView.tvSource.text = it
        })

        viewModel.publishedAt.observe(this, {

            it?.let {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.US)
                val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm a", Locale.US)

                val date = inputFormat.parse(it)
                val outputText = date?.let { value -> outputFormat.format(value) }

                itemView.tvTime.text =  String.format(
                    itemView.tvAuthor.context.getString(R.string.news_feed_published_label, outputText)
                )
            } ?: run {
                String.format(
                    itemView.tvAuthor.context.getString(R.string.news_feed_published_label),
                    itemView.tvAuthor.context.getString(R.string.news_feed_label_error)
                )
            }

        })

        viewModel.author.observe(this, {
            it?.let {
                itemView.tvAuthor.text =
                    String.format(
                        itemView.tvAuthor.context.getString(R.string.news_feed_author_label, it)
                    )
            } ?: run {
                itemView.tvAuthor.text =
                    String.format(
                        itemView.tvAuthor.context.getString(R.string.news_feed_author_label),
                        itemView.tvAuthor.context.getString(R.string.news_feed_label_error)
                    )
            }
        })

        viewModel.imageURL.observe(this, { url ->
            url?.let {
                val glideRequest = Glide
                    .with(itemView.ivImage.context)
                    .load(GlideHelper.getProtectedUrl(url))
                    .error(ContextCompat.getDrawable(itemView.context, R.drawable.ic_error))
                    .placeholder(
                        ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.ic_placeholder
                        )
                    )
                    .centerCrop()
                glideRequest.into(itemView.ivImage)
            }
        })

        viewModel.title.observe(this, {
            itemView.tvTitle.text = it
        })
    }
}