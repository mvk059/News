package com.mvk.news.data.repository

import android.content.Context
import android.media.Image
import android.provider.SyncStateContract
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.mvk.news.R
import com.mvk.news.data.model.NewsCategory
import com.mvk.news.utils.common.Constants
import com.mvk.news.utils.common.readInput
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsCategoryRepository @Inject constructor(
        private val context: Context,
) {

    /**
     * Get the list of news categories
     *
     * @return List of news categories
     */
    fun getCategoryList(): List<NewsCategory> {
        var json: String? = ""
        try {
            val inputStream = context.assets.open(Constants.CATEGORY_ASSET_FILE_NAME)
            json = inputStream.readInput()
        } catch (e: IOException) {
            Toast.makeText(context, context.getString(R.string.network_default_error), Toast.LENGTH_SHORT).show()
        }
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()
        return gson.fromJson(json, Array<NewsCategory>::class.java).toList()
    }
}