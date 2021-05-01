package com.mvk.news.data.repository

import com.mvk.news.data.remote.NetworkService
import com.mvk.news.data.remote.request.NewsHeadlinesRequest
import com.mvk.news.data.remote.response.NewsHeadlinesResponse
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(
    private val networkService: NetworkService,
) {

    fun doNewsHeadlinesCall(newsHeadlinesRequest: NewsHeadlinesRequest): Single<NewsHeadlinesResponse> =
        networkService.doNewsHeadlinesCall(newsHeadlinesRequest)
            .map {
                NewsHeadlinesResponse(
                    it.status,
                    it.articles
                )
            }
}