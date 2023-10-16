package com.danielsenik.sportevents.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danielsenik.sportevents.model.ui.Event
import com.danielsenik.sportevents.R
import com.danielsenik.sportevents.databinding.ItemEventBinding
import com.danielsenik.sportevents.util.TimeUtils
import com.danielsenik.sportevents.view.listener.OnFavoriteEventAddedRemovedListener

class EventRecyclerAdapter() : RecyclerView.Adapter<EventRecyclerAdapter.EventViewHolder>() {
    private var events: List<Event> = emptyList()

    private lateinit var onFavoriteEventAddedRemovedListener: OnFavoriteEventAddedRemovedListener

    fun setOnFavoriteEventAddedRemovedListener(onFavoriteEventAddedRemovedListener: OnFavoriteEventAddedRemovedListener) {
        this.onFavoriteEventAddedRemovedListener = onFavoriteEventAddedRemovedListener
    }

    fun setEvents(events: List<Event>) {
        this.events = events
        notifyDataSetChanged()
    }

    class EventViewHolder(
        private val binding: ItemEventBinding,
        private val onFavoriteEventAddedRemovedListener: OnFavoriteEventAddedRemovedListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            if (event.hasTimePassed) {
                binding.timeLeftText.text = "-||-"
                binding.enabledDisabledView.visibility = View.VISIBLE
            } else {
                binding.timeLeftText.text = TimeUtils.formatTimeLeft(event.timeLeft)
                binding.enabledDisabledView.visibility = View.GONE
            }

            binding.favoriteButton.setOnClickListener {
                onFavoriteEventAddedRemovedListener.onAddedRemoved(event)
            }

            if (event.isFavorite) {
                binding.favoriteButton.setImageResource(R.drawable.ic_star_filled)
            } else {
                binding.favoriteButton.setImageResource(R.drawable.ic_star_unfilled)
            }

            binding.homeText.text = event.home
            binding.visitorsText.text = event.visitors
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            ItemEventBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onFavoriteEventAddedRemovedListener
        )
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }
}