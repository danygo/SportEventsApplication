package com.danielsenik.sportevents.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danielsenik.sportevents.db.dao.FavoriteEventDao
import com.danielsenik.sportevents.model.db.FavoriteEventEntity

@Database(entities = [FavoriteEventEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteEventDao(): FavoriteEventDao
}