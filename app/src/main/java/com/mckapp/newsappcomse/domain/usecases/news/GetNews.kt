package com.mckapp.newsappcomse.domain.usecases.news

import androidx.paging.PagingData
import com.mckapp.newsappcomse.domain.model.Article
import com.mckapp.newsappcomse.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNews (
    private val newsRepository: NewsRepository
) {
    operator fun invoke(sources: List<String>): Flow<PagingData<Article>> {
        return newsRepository.getNews(sources = sources)
    }
}