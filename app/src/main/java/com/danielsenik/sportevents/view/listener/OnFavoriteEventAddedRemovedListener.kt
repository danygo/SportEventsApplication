package com.danielsenik.sportevents.view.listener

import com.danielsenik.sportevents.model.ui.Event

interface OnFavoriteEventAddedRemovedListener {
    fun onAddedRemoved(event: Event)
}