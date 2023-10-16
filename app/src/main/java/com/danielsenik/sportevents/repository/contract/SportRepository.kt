package com.danielsenik.sportevents.repository.contract

import com.danielsenik.sportevents.model.util.Resource
import com.danielsenik.sportevents.model.remote.SportModel

interface SportRepository {
    suspend fun getSports(): Resource<List<SportModel>>
}