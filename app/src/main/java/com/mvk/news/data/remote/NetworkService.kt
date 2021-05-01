package com.mvk.news.data.remote

import com.mvk.news.data.remote.request.NewsHeadlinesRequest
import com.mvk.news.data.remote.response.NewsHeadlinesResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @POST(Endpoints.TOP_HEADLINES)
    fun doNewsHeadlinesCall(
        @Body request: NewsHeadlinesRequest,
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<NewsHeadlinesResponse>
}
