package com.mckapp.newsappcomse.di

import android.app.Application
import com.mckapp.newsappcomse.data.manager.LocalUserImpl
import com.mckapp.newsappcomse.domain.manager.LocalUserManager
import com.mckapp.newsappcomse.domain.usecases.AppEntryUseCases
import com.mckapp.newsappcomse.domain.usecases.ReadAppEntry
import com.mckapp.newsappcomse.domain.usecases.SaveAppEntry
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
}