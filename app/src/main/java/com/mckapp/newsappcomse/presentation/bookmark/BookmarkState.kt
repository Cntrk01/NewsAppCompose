package com.mckapp.newsappcomse.presentation.bookmark

import com.mckapp.newsappcomse.domain.model.Article

data class BookmarkState(
    val articles : List<Article> ? = null
)
