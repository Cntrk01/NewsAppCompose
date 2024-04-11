package com.mckapp.newsappcomse.domain.usecases.news

import com.mckapp.newsappcomse.domain.model.Article
import com.mckapp.newsappcomse.domain.repository.NewsRepository

class SelectArticle (
    private val newsRepository: NewsRepository
){
    suspend operator fun invoke(url: String) : Article?{
        return newsRepository.selectArticle(url = url)
    }
}