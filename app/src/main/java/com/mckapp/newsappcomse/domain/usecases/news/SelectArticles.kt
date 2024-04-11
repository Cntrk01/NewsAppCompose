package com.mckapp.newsappcomse.domain.usecases.news

import com.mckapp.newsappcomse.data.local.NewsDao
import com.mckapp.newsappcomse.domain.model.Article
import com.mckapp.newsappcomse.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SelectArticles (
    private val newsRepository: NewsRepository
) {
    operator fun invoke() : Flow<List<Article>>{
        return newsRepository.selectArticles()
    }
}