package com.mckapp.newsappcomse.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mckapp.newsappcomse.domain.model.Article
import com.mckapp.newsappcomse.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
): ViewModel() {
    var sideEffect by mutableStateOf<String?>(null)
        private set

    fun onEvent(events: DetailEvents){
        when(events){
            is DetailEvents.UpsertDeleteArticle -> {
               viewModelScope.launch (Dispatchers.IO){
                   val article = newsUseCases.selectArticle(events.article.url)

                   if (article==null){
                       upsertArticle(events.article)
                   }else{
                        deleteArticle(events.article)
                   }
               }
            }
            is DetailEvents.RemoveSideEffect -> {
                sideEffect = null
            }
        }
    }
    private suspend fun upsertArticle(article: Article) {
        newsUseCases.upsertArticle(article = article)
        sideEffect = "Article Saved"
    }
    private suspend fun deleteArticle(article: Article) {
        newsUseCases.deleteArticle(article = article)
        sideEffect = "Article Deleted"
    }
}