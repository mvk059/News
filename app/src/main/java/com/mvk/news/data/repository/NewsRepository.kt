package com.mvk.news.data.repository

import com.mvk.news.data.remote.NetworkService
import com.mvk.news.data.remote.response.NewsHeadlinesResponse
import com.mvk.news.utils.common.Constants
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val networkService: NetworkService,
) {

    fun doNewsHeadlinesCall(
        country: String,
        category: String,
        pageSize: Int = Constants.PAGE_SIZE,
        page: Int
    ): Single<NewsHeadlinesResponse> =
        networkService.doNewsHeadlinesCall(country, category, pageSize, page)
            .map {
                NewsHeadlinesResponse(
                    it.status,
                    it.articles
                )
            }

    fun doSearchCall(
        searchQuery: String,
        pageSize: Int = Constants.PAGE_SIZE,
        page: Int) =
        networkService.doSearchCall(searchQuery, pageSize, page)
            .map {
                NewsHeadlinesResponse(
                    it.status,
                    it.articles
                )
            }

}