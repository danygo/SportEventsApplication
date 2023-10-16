package com.danielsenik.sportevents.di

import android.content.Context
import androidx.room.Room
import com.danielsenik.sportevents.db.AppDatabase
import com.danielsenik.sportevents.db.dao.FavoriteEventDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_db")
            .build();
    }

    @Provides
    fun provideFavoriteEventDao(appDatabase: AppDatabase): FavoriteEventDao {
        return appDatabase.favoriteEventDao()
    }
}