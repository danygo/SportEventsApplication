package com.danielsenik.sportevents.model.remote

import com.google.gson.annotations.SerializedName

data class EventModel(
    @SerializedName("i") val eventId: String,
    @SerializedName("si") val sportId: String,
    @SerializedName("d") val eventName: String,
    @SerializedName("tt") val startEventTime: Long
)
