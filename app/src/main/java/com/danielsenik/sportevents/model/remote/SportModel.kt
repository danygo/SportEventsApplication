package com.danielsenik.sportevents.model.remote

import com.google.gson.annotations.SerializedName

data class SportModel(
    @SerializedName("i") val sportId: String,
    @SerializedName("d") val sportName: String,
    @SerializedName("e") val events: List<EventModel>
)
