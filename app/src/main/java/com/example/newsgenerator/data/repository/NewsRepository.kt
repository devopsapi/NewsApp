package com.example.newsgenerator.data.repository

import com.example.newsgenerator.data.models.NewsResponse

interface NewsRepository {
    suspend fun getHeadlines(country: String): NewsResponse
    suspend fun getSpecificHeadlines(query:String):NewsResponse
}