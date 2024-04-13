package com.mckapp.newsappcomse.data.remote.dto

import com.mckapp.newsappcomse.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)