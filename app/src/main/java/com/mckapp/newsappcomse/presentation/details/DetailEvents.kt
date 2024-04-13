package com.mckapp.newsappcomse.presentation.details

import com.mckapp.newsappcomse.domain.model.Article

sealed class DetailEvents {
    data class UpsertDeleteArticle(val article: Article) : DetailEvents()
    
    data object RemoveSideEffect : DetailEvents()
}