package com.mckapp.newsappcomse.domain.usecases.news

import androidx.paging.PagingData
import com.mckapp.newsappcomse.domain.model.Article
import com.mckapp.newsappcomse.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SearchNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(searchQuery : String ,sources: List<String>): Flow<PagingData<Article>> {
        return newsRepository.searchNews(searchQuery=searchQuery,sources = sources)
    }
}