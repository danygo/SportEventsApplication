package com.danielsenik.sportevents.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_event_table")
data class FavoriteEventEntity(
    @PrimaryKey val id: String
)