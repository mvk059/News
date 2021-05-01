package com.mvk.news.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsSource(
    @Expose
    @SerializedName("status")
    var id: String?,

    @Expose
    @SerializedName("name")
    var name: String?,
)