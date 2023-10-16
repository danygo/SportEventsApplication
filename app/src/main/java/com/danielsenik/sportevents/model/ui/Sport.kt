package com.danielsenik.sportevents.model.ui

import com.danielsenik.sportevents.model.enumerator.SortOption

data class Sport(
    val sportName: String,
    var sortOption: SortOption,
    var isExpanded: Boolean,
    var events: List<Event>
)