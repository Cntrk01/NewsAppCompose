package com.mckapp.newsappcomse.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mckapp.newsappcomse.domain.model.Article

@Database(entities = [Article::class], version = 2, exportSchema = false)
@TypeConverters(NewsTypeConventer::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract val newsDao : NewsDao
}