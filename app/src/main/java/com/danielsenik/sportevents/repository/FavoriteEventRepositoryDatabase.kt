package com.danielsenik.sportevents.repository

import com.danielsenik.sportevents.db.dao.FavoriteEventDao
import com.danielsenik.sportevents.model.db.FavoriteEventEntity
import com.danielsenik.sportevents.repository.contract.FavoriteEventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteEventRepositoryDatabase @Inject constructor(private val favoriteEventDao: FavoriteEventDao) :
    FavoriteEventRepository {
    override suspend fun isFavorite(eventId: String): Boolean {
        return withContext(Dispatchers.IO) {
            favoriteEventDao.isEventFavorite(eventId) != null
        }
    }

    override suspend fun addToFavoriteEvents(eventId: String) {
        return withContext(Dispatchers.IO) {
            favoriteEventDao.addFavoriteEvent(
                FavoriteEventEntity(eventId)
            )
        }
    }

    override suspend fun removeFromFavoriteEvents(eventId: String) {
        return withContext(Dispatchers.IO) {
            favoriteEventDao.removeFavoriteEvent(
                FavoriteEventEntity(eventId)
            )
        }
    }
}