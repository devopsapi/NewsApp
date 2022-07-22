package com.example.newsgenerator.data.api

import com.example.newsgenerator.data.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getHeadlines(
        @Query("country") country: String
    ): NewsResponse

    @GET("everything")
    suspend fun getSpecificHeadlines(
        @Query("q") query: String
    ): NewsResponse
}