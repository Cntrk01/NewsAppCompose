package com.mckapp.newsappcomse.domain.usecases.news

import com.mckapp.newsappcomse.data.local.NewsDao
import com.mckapp.newsappcomse.domain.model.Article
import kotlinx.coroutines.flow.Flow

class SelectArticles (
    private val newsDao: NewsDao
) {
    operator fun invoke() : Flow<List<Article>>{
        return newsDao.getArticles()
    }
}