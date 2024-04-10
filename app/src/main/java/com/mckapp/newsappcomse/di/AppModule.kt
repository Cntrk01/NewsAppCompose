package com.mckapp.newsappcomse.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mckapp.newsappcomse.data.local.NewsDao
import com.mckapp.newsappcomse.data.local.NewsDatabase
import com.mckapp.newsappcomse.data.local.NewsTypeConventer
import com.mckapp.newsappcomse.data.manager.LocalUserImpl
import com.mckapp.newsappcomse.data.remote.NewsApi
import com.mckapp.newsappcomse.data.repository.NewsRepositoryImpl
import com.mckapp.newsappcomse.domain.manager.LocalUserManager
import com.mckapp.newsappcomse.domain.repository.NewsRepository
import com.mckapp.newsappcomse.domain.usecases.app_entry.AppEntryUseCases
import com.mckapp.newsappcomse.domain.usecases.app_entry.ReadAppEntry
import com.mckapp.newsappcomse.domain.usecases.app_entry.SaveAppEntry
import com.mckapp.newsappcomse.domain.usecases.news.DeleteArticle
import com.mckapp.newsappcomse.domain.usecases.news.GetNews
import com.mckapp.newsappcomse.domain.usecases.news.NewsUseCases
import com.mckapp.newsappcomse.domain.usecases.news.SearchNews
import com.mckapp.newsappcomse.domain.usecases.news.SelectArticles
import com.mckapp.newsappcomse.domain.usecases.news.UpsertArticle
import com.mckapp.newsappcomse.utils.Constants.BASE_URL
import com.mckapp.newsappcomse.utils.Constants.DATABASE_NAME
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
        newsRepository: NewsRepository,
        newsDao: NewsDao
    ): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository),
            upsertArticle = UpsertArticle(newsDao = newsDao),
            selectArticles = SelectArticles(newsDao = newsDao),
            deleteArticle = DeleteArticle(newsDao=newsDao)
        )
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ) : NewsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = DATABASE_NAME
        ).addTypeConverter(NewsTypeConventer())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ) : NewsDao = newsDatabase.newsDao
 }