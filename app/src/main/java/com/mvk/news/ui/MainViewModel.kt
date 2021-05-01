package com.mvk.news.ui

import com.mvk.news.data.repository.NetworkRepository
import com.mvk.news.ui.base.BaseViewModel
import com.mvk.news.utils.log.Logger
import com.mvk.news.utils.network.NetworkHelper
import com.mvk.news.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    networkRepository: NetworkRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    init {
        compositeDisposable.addAll(
            networkRepository.doNewsHeadlinesCall(country = "us", category = "business", page = 1)
                .subscribeOn(schedulerProvider.io())
                .subscribe(
                    {
                        Logger.d("OKHTTP", it.articles?.title.toString())
                    },
                    {
                        handleNetworkError(it)
                    }
                )
        )
    }

    override fun onCreate() {

    }

}
