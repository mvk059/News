package com.mvk.news.utils.common

import android.content.Context
import android.widget.Toast

object Toaster {
    fun show(context: Context, text: CharSequence) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}