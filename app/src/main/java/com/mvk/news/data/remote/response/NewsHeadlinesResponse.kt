package com.mvk.news.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsHeadlinesResponse(
    @Expose
    @SerializedName("status")
    var status: String?,

    @Expose
    @SerializedName("articles")
    var articles: NewsArticles?,
) {
    data class NewsArticles(
        @Expose
        @SerializedName("status")
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

    data class NewsSource(
        @Expose
        @SerializedName("status")
        var id: String?,
        @Expose
        @SerializedName("name")
        var name: String?,
    )
}