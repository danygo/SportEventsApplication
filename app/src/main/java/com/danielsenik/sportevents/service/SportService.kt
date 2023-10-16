package com.danielsenik.sportevents.service

import com.danielsenik.sportevents.model.remote.SportModel
import retrofit2.http.GET

interface SportService {
    companion object {
        const val BASE_URL = "https://618d3aa7fe09aa001744060a.mockapi.io/api/"
    }

    @GET("sports")
    suspend fun getSports(): List<SportModel>
}