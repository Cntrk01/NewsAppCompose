package com.mckapp.newsappcomse.domain.usecases.news

import com.mckapp.newsappcomse.data.local.NewsDao
import com.mckapp.newsappcomse.domain.model.Article

class DeleteArticle(
    private val newsDao: NewsDao
) {
    suspend operator fun invoke(article: Article){
        newsDao.deleteArticle(article = article)
    }
}