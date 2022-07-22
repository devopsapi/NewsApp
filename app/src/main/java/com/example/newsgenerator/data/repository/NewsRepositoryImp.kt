package com.example.newsgenerator.data.repository

import com.example.newsgenerator.data.api.NewsApi
import com.example.newsgenerator.data.models.NewsResponse
import javax.inject.Inject

class NewsRepositoryImp @Inject constructor(private val newsApi: NewsApi) : NewsRepository {

    override suspend fun getHeadlines(country: String) = newsApi.getHeadlines(country)
    override suspend fun getSpecificHeadlines(query: String): NewsResponse =
        newsApi.getSpecificHeadlines(query)

}