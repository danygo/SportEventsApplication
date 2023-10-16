package com.danielsenik.sportevents.repository

import android.util.Log
import com.danielsenik.sportevents.model.util.Resource
import com.danielsenik.sportevents.service.SportService
import com.danielsenik.sportevents.model.remote.SportModel
import com.danielsenik.sportevents.repository.contract.SportRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SportRepositoryApi @Inject constructor(private val sportService: SportService) :
    SportRepository {
    private val TAG = "SportRepositoryApi"

    override suspend fun getSports(): Resource<List<SportModel>> {
        return withContext(Dispatchers.IO) {
            try {
                val data = sportService.getSports().map {
                    SportModel(
                        it.sportId,
                        it.sportName,
                        it.events
                    )
                }
                if (data.isNotEmpty()) {
                    Resource.OK(data)
                } else {
                    Resource.ERROR(null, Exception("No Sports"))
                }
            } catch (exception: Exception) {
                Log.e(TAG, exception.toString())
                Resource.ERROR(null, exception)
            }
        }
    }
}