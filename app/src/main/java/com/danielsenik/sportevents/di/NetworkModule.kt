package com.danielsenik.sportevents.di

import com.danielsenik.sportevents.repository.FavoriteEventRepositoryDatabase
import com.danielsenik.sportevents.repository.contract.FavoriteEventRepository
import com.danielsenik.sportevents.repository.contract.SportRepository
import com.danielsenik.sportevents.repository.SportRepositoryApi
import com.danielsenik.sportevents.service.SportService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideService(): SportService {
        return Retrofit.Builder()
            .baseUrl(SportService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SportService::class.java)
    }

    @Provides
    fun provideSportRepository(sportRepositoryApi: SportRepositoryApi) : SportRepository {
        return sportRepositoryApi
    }

    @Provides
    fun provideFavoriteEventDatabaseRepository(favoriteEventRepositoryDatabase: FavoriteEventRepositoryDatabase) : FavoriteEventRepository {
        return favoriteEventRepositoryDatabase
    }
}