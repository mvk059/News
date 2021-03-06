package com.mvk.news.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mvk.news.data.model.NewsArticles
import com.mvk.news.data.model.NewsSource

/**
 * Model for News API response
 */
data class NewsHeadlinesResponse(
    @Expose
    @SerializedName("status")
    var status: String?,

    @Expose
    @SerializedName("articles")
    var articles: List<NewsArticles>?,
)



