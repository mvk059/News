package com.mvk.news.data.remote

import com.mvk.news.data.remote.response.NewsHeadlinesResponse
import io.reactivex.Single
import retrofit2.http.*
import javax.inject.Singleton

/**
 * List of APIs
 */
@Singleton
interface NetworkService {

    /**
     * Get latest headlines
     */
    @GET(Endpoints.TOP_HEADLINES)
    fun doNewsHeadlinesCall(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<NewsHeadlinesResponse>

    @GET(Endpoints.EVERYTHING)
    fun doSearchCall(
        @Query("q") searchQuery: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
        @Header(Networking.HEADER_API_KEY) apiKey: String = Networking.API_KEY // default value set when Networking create is called
    ): Single<NewsHeadlinesResponse>
}
