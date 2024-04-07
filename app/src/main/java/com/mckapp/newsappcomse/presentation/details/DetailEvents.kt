package com.mckapp.newsappcomse.presentation.details

sealed class DetailEvents {
    data object SaveArticle : DetailEvents()
}