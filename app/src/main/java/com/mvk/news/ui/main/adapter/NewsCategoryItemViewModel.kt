package com.mvk.news.ui.main.adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mvk.news.data.model.NewsCategory
import com.mvk.news.ui.base.BaseItemViewModel
import com.mvk.news.utils.network.NetworkHelper
import com.mvk.news.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewsCategoryItemViewModel @Inject constructor(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
) : BaseItemViewModel<NewsCategory>(schedulerProvider, compositeDisposable, networkHelper) {

    val categoryName: LiveData<String> = Transformations.map(data) { it?.category }

    override fun onCreate() {

    }
}