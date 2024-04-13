package com.mckapp.newsappcomse.domain.usecases.news

import com.mckapp.newsappcomse.data.local.NewsDao
import com.mckapp.newsappcomse.domain.model.Article
import com.mckapp.newsappcomse.domain.repository.NewsRepository

class DeleteArticle(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(article: Article){
        newsRepository.deleteArticle(article = article)
    }
}