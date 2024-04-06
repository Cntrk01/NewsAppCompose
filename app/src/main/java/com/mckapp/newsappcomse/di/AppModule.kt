package com.mckapp.newsappcomse.di

import android.app.Application
import com.mckapp.newsappcomse.data.manager.LocalUserImpl
import com.mckapp.newsappcomse.data.remote.NewsApi
import com.mckapp.newsappcomse.data.repository.NewsRepositoryImpl
import com.mckapp.newsappcomse.domain.manager.LocalUserManager
import com.mckapp.newsappcomse.domain.repository.NewsRepository
import com.mckapp.newsappcomse.domain.usecases.app_entry.AppEntryUseCases
import com.mckapp.newsappcomse.domain.usecases.app_entry.ReadAppEntry
import com.mckapp.newsappcomse.domain.usecases.app_entry.SaveAppEntry
import com.mckapp.newsappcomse.domain.usecases.news.GetNews
import com.mckapp.newsappcomse.domain.usecases.news.NewsUseCases
import com.mckapp.newsappcomse.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManger(
        application: Application
    ): LocalUserManager = LocalUserImpl(context = application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManger: LocalUserManager
    ): AppEntryUseCases = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManger),
        saveAppEntry = SaveAppEntry(localUserManger)
    )

    @Provides
    @Singleton
    fun provideApiInstance(): NewsApi {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi
    ): NewsRepository {
        return NewsRepositoryImpl(newsApi)
    }

    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository
    ): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository),
        )
    }
 }