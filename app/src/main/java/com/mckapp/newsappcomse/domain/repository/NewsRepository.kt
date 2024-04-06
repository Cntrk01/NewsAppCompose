package com.mckapp.newsappcomse.domain.repository

import androidx.paging.PagingData
import com.mckapp.newsappcomse.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(sources : List<String>) : Flow<PagingData<Article>>
}