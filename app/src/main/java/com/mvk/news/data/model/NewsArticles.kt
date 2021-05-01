package com.mvk.news.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsArticles(
    @Expose
    @SerializedName("source")
    var source: NewsSource?,

    @Expose
    @SerializedName("author")
    var author: String?,

    @Expose
    @SerializedName("title")
    var title: String?,

    @Expose
    @SerializedName("description")
    var description: String?,

    @Expose
    @SerializedName("url")
    var url: String?,

    @Expose
    @SerializedName("urlToImage")
    var urlToImage: String?,

    @Expose
    @SerializedName("publishedAt")
    var publishedAt: String?,

    @Expose
    @SerializedName("content")
    var content: String?,
)