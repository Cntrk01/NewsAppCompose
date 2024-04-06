package com.mckapp.newsappcomse.presentation.search

import androidx.paging.PagingData
import com.mckapp.newsappcomse.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery : String = "",
    val articles : Flow<PagingData<Article>> ?= null
)
