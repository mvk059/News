package com.mvk.news.ui.home.adapter

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.mvk.news.R
import com.mvk.news.data.model.NewsCategory
import com.mvk.news.ui.base.BaseAdapter
import kotlinx.android.synthetic.main.item_view_news_category.view.*

class NewsCategoryAdapter(
        parentLifecycle: Lifecycle,
        private val posts: ArrayList<NewsCategory>
) : BaseAdapter<NewsCategory, NewsCategoryItemViewHolder>(parentLifecycle, posts) {

    private var rowIndex = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsCategoryItemViewHolder(parent)

    override fun onBindViewHolder(holder: NewsCategoryItemViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.tvCategoryName.setOnClickListener {
            rowIndex = position
            holder.refreshNewsFeed()
            notifyDataSetChanged()
        }
        if (rowIndex == position) {
            val context = holder.itemView.tvCategoryName.context
            holder.itemView.tvCategoryName.setTextColor(context.getColor(R.color.white))
            holder.itemView.tvCategoryName.setBackgroundColor(context.getColor(R.color.black))
        } else {
            val context = holder.itemView.tvCategoryName.context
            holder.itemView.tvCategoryName.setTextColor(context.getColor(R.color.black))
            holder.itemView.tvCategoryName.setBackgroundColor(context.getColor(R.color.white))
        }
    }

    fun clearSelection() {
        rowIndex = -1
        notifyDataSetChanged()
    }
}