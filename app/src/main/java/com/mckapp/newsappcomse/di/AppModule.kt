package com.mckapp.newsappcomse.di

import android.app.Application
import com.mckapp.newsappcomse.data.manager.LocalUserImpl
import com.mckapp.newsappcomse.data.remote.NewsApi
import com.mckapp.newsappcomse.data.repository.NewsRepositoryImpl
import com.mckapp.newsappcomse.domain.manager.LocalUserManager
import com.mckapp.newsappcomse.domain.usecases.app_entry.AppEntryUseCases
import com.mckapp.newsappcomse.domain.usecases.app_entry.ReadAppEntry
import com.mckapp.newsappcomse.domain.usecases.app_entry.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserImpl(context = application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )

    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: NewsApi) : NewsRepositoryImpl{
        return NewsRepositoryImpl(newsApi = newsApi)
    }
}