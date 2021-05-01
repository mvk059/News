package com.mvk.news.data.remote.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsHeadlinesRequest(
    @Expose
    @SerializedName("country")
    var country: String,

    @Expose
    @SerializedName("country")
    var category: String,

    @Expose
    @SerializedName("country")
    var pageSize: Int,

    @Expose
    @SerializedName("country")
    var page: Int,
)
