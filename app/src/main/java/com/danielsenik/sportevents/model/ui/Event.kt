package com.danielsenik.sportevents.model.ui

data class Event(
    val eventId: String,
    val startTime: Long,
    val timeLeft: Long,
    val isFavorite: Boolean,
    val hasTimePassed: Boolean,
    val home: String,
    val visitors: String
)
