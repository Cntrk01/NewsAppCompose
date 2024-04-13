package com.mckapp.newsappcomse.data.remote

import com.mckapp.newsappcomse.data.remote.dto.NewsResponse
import com.mckapp.newsappcomse.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    suspend fun getNews(
        @Query("page") page : Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey : String = Constants.API_KEY
    ) : NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q") searchQuery : String,
        @Query("page") page : Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey : String = Constants.API_KEY
    ) : NewsResponse
}