package com.mvk.news.utils.common

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    /**
     * Format time
     *
     * @param time Time to format
     * @return Formatted time
     */
    fun formatTime(time: String): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.US)
        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm a", Locale.US)

        val date = inputFormat.parse(time)
        return date?.let { value -> outputFormat.format(value) }
    }
}