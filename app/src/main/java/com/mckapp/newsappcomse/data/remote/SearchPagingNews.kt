package com.mckapp.newsappcomse.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mckapp.newsappcomse.domain.model.Article
import java.lang.Exception

class SearchPagingNews (
    private val searchApi : NewsApi,
    private val searchQuery : String,
    private val sources : String
) : PagingSource<Int,Article>(){

    private var totalNewsCount = 0

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {anchorPosition->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1

        return try {
            val newsResponse = searchApi.searchNews(searchQuery=searchQuery,page = page , sources = sources)
            totalNewsCount += newsResponse.articles.size
            //distinctBy ile benzersiz olan title objelerini getirip yeni bir liste oluşturacağız.
            val articles = newsResponse.articles.distinctBy { it.title }
            LoadResult.Page(
                data = articles,
                nextKey = if (totalNewsCount==newsResponse.totalResults) null else page+1,
                prevKey = null
            )
        }catch (e : Exception){
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }
}