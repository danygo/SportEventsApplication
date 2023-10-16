package com.danielsenik.sportevents.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielsenik.sportevents.model.enumerator.SortOption
import com.danielsenik.sportevents.model.ui.Event
import com.danielsenik.sportevents.repository.contract.FavoriteEventRepository
import com.danielsenik.sportevents.model.util.Resource
import com.danielsenik.sportevents.model.ui.Sport
import com.danielsenik.sportevents.repository.contract.SportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class SportViewModel @Inject constructor(
    private val sportRepository: SportRepository,
    private val favoriteEventRepository: FavoriteEventRepository
) :
    ViewModel() {
    private val timer = object : CountDownTimer(1 * 60 * 60 * 1000L, 1000L) {
        override fun onTick(millisUntilFinished: Long) {
            updateCountdownTimer()
        }

        override fun onFinish() {

        }
    }

    private val sports = MutableLiveData<Resource<List<Sport>>>()

    init {
        getSports()
    }

    fun getObservableSports(): LiveData<Resource<List<Sport>>> {
        return sports
    }

    fun getSports() {
        viewModelScope.launch {
            sports.postValue(Resource.LOADING())

            val result = sportRepository.getSports()

            when (result) {
                is Resource.OK -> {
                    if (result.data.isNullOrEmpty()) {
                        sports.postValue(Resource.ERROR(null, result.error))
                    } else {
                        sports.postValue(Resource.OK(result.data.map { sportModel ->
                            Sport(
                                sportModel.sportName,
                                SortOption.ENDING_SOONEST_FIRST,
                                true,
                                sort(sportModel.events.map { eventModel ->
                                    val startTime = eventModel.startEventTime * 1000L
                                    val currentTime = Calendar.getInstance().timeInMillis

                                    Event(
                                        eventModel.eventId,
                                        startTime,
                                        startTime - currentTime,
                                        favoriteEventRepository.isFavorite(eventModel.eventId),
                                        currentTime >= startTime,
                                        eventModel.eventName.split("-")[0],
                                        eventModel.eventName.split("-")[1]
                                    )
                                }, SortOption.ENDING_SOONEST_FIRST)
                            )
                        }))
                        timer.start()
                    }
                }

                is Resource.LOADING -> {
                    sports.postValue(Resource.LOADING())
                }

                is Resource.ERROR -> {
                    sports.postValue(Resource.ERROR(null, result.error))
                }
            }
        }
    }

    fun expandMinimizeSport(sport: Sport) {
        viewModelScope.launch {
            sports.postValue(Resource.OK(sports.value?.data?.map {
                if (it.sportName == sport.sportName) {
                    it.copy(isExpanded = !sport.isExpanded)
                } else {
                    it
                }
            }))
        }
    }

    fun sortSportEvents(sport: Sport, sortOption: SortOption) {
        viewModelScope.launch {
            sports.postValue(Resource.OK(sports.value?.data?.map {
                if (it.sportName == sport.sportName) {
                    it.copy(sortOption = sortOption, events = sort(it.events, sortOption))
                } else {
                    it
                }
            }))
        }
    }

    private fun sort(events: List<Event>, sortOption: SortOption): List<Event> {
        return when (sortOption) {
            SortOption.ENDING_SOONEST_FIRST -> {
                events.sortedBy { event ->
                    event.startTime
                }.sortedBy { event ->
                    event.hasTimePassed
                }
            }

            SortOption.FAVORITE_FIRST -> {
                events.sortedByDescending { event ->
                    event.isFavorite
                }
            }
        }
    }

    fun addRemoveFavoriteEvent(event: Event) {
        viewModelScope.launch {
            if (event.isFavorite) {
                favoriteEventRepository.removeFromFavoriteEvents(event.eventId)
            } else {
                favoriteEventRepository.addToFavoriteEvents(event.eventId)
            }

            sports.postValue(Resource.OK(sports.value?.data?.map { sport ->
                sport.copy(events = sport.events.map {
                    if (it.eventId == event.eventId) {
                        it.copy(isFavorite = !event.isFavorite)
                    } else {
                        it
                    }
                })
            }))
        }
    }

    fun updateCountdownTimer() {
        viewModelScope.launch {
            val currentTime = Calendar.getInstance().timeInMillis

            sports.postValue(Resource.OK(sports.value?.data?.map { sport ->
                sport.copy(events = sport.events.map {
                    it.copy(
                        timeLeft = it.startTime - currentTime,
                        hasTimePassed = currentTime >= it.startTime
                    )
                })
            }))
        }
    }
}