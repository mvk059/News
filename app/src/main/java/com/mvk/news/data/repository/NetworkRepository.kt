package com.mvk.news.data.repository

import com.mvk.news.data.remote.NetworkService
import com.mvk.news.data.remote.response.NewsHeadlinesResponse
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(
    private val networkService: NetworkService,
) {

    fun doNewsHeadlinesCall(
        country: String,
        category: String,
        pageSize: Int = 20,
        page: Int
    ): Single<NewsHeadlinesResponse> =
        networkService.doNewsHeadlinesCall(country, category, pageSize, page)
            .map {
                NewsHeadlinesResponse(
                    it.status,
                    it.articles
                )
            }
}