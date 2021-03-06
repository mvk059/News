package com.mvk.news.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Model for News Source
 */
data class NewsSource(
    @Expose
    @SerializedName("id")
    var id: String?,

    @Expose
    @SerializedName("name")
    var name: String?,
)