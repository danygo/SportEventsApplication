package com.danielsenik.sportevents.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.danielsenik.sportevents.model.db.FavoriteEventEntity

@Dao
interface FavoriteEventDao {
    @Query("SELECT * FROM favorite_event_table WHERE id=:id")
    fun isEventFavorite(id: String): FavoriteEventEntity?

    @Insert
    fun addFavoriteEvent(event: FavoriteEventEntity)

    @Delete
    fun removeFavoriteEvent(event: FavoriteEventEntity)
}