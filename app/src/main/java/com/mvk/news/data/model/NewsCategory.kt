package com.mvk.news.data.model

import com.google.gson.annotations.SerializedName

data class NewsCategory(
        @SerializedName("category")
        var category: String?
)
