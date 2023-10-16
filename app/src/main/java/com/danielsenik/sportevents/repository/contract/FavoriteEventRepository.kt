package com.danielsenik.sportevents.repository.contract

interface FavoriteEventRepository {
    suspend fun isFavorite(eventId: String): Boolean
    suspend fun addToFavoriteEvents(eventId: String)
    suspend fun removeFromFavoriteEvents(eventId: String)
}