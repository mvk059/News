package com.mvk.news.data.model

import com.google.gson.annotations.SerializedName

/**
 * Model for News Categories
 */
data class NewsCategory(
    @SerializedName("category")
    var category: String?
)
