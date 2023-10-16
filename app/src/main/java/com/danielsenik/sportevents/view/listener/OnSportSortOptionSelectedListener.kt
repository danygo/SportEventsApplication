package com.danielsenik.sportevents.view.listener

import com.danielsenik.sportevents.model.enumerator.SortOption
import com.danielsenik.sportevents.model.ui.Sport

interface OnSportSortOptionSelectedListener {
    fun onSortOptionSelected(sport: Sport, sortOption: SortOption)
}