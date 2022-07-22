package com.example.newsgenerator.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsgenerator.data.models.Article
import com.example.newsgenerator.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    ViewModel() {
    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _progressBar = MutableLiveData(false)
    val progressBar: LiveData<Boolean> = _progressBar

    fun getHeadlines(country: String) {
        _progressBar.postValue(true)
        viewModelScope.launch {
            val response = newsRepository.getHeadlines(country)
            if (response.status == "ok") {
                _articles.postValue(response.articles)
            } else {
                _errorMessage.postValue(response.status)
            }
            _progressBar.postValue(false)
        }
    }
    fun getSpecificHeadlines(query: String) {
        _progressBar.postValue(true)
        viewModelScope.launch {
            val response = newsRepository.getSpecificHeadlines(query)
            if (response.status == "ok") {
                _articles.postValue(response.articles)
            } else {
                _errorMessage.postValue(response.status)
            }
            _progressBar.postValue(false)
        }
    }

    fun getArticle(position: Int): Article = articles.value!![position]
}