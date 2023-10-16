package com.danielsenik.sportevents.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danielsenik.sportevents.R
import com.danielsenik.sportevents.view.listener.OnSportExpandedMinimizedListener
import com.danielsenik.sportevents.model.ui.Sport
import com.danielsenik.sportevents.view.adapter.SportRecyclerAdapter.SportViewHolder
import com.danielsenik.sportevents.databinding.ItemSportBinding
import com.danielsenik.sportevents.model.enumerator.SortOption
import com.danielsenik.sportevents.view.listener.OnFavoriteEventAddedRemovedListener
import com.danielsenik.sportevents.view.listener.OnSportSortOptionSelectedListener

class SportRecyclerAdapter() : RecyclerView.Adapter<SportViewHolder>() {
    private var sports: List<Sport> = emptyList()

    private lateinit var onSportExpandedMinimizedListener: OnSportExpandedMinimizedListener
    private lateinit var onSportSortOptionSelectedListener: OnSportSortOptionSelectedListener
    private lateinit var onFavoriteEventAddedRemovedListener: OnFavoriteEventAddedRemovedListener

    fun setOnExpandedMinimizedListener(onSportExpandedMinimizedListener: OnSportExpandedMinimizedListener) {
        this.onSportExpandedMinimizedListener = onSportExpandedMinimizedListener
    }

    fun setOnSportSortOptionSelectedListener(onSportSortOptionSelectedListener: OnSportSortOptionSelectedListener) {
        this.onSportSortOptionSelectedListener = onSportSortOptionSelectedListener
    }

    fun setOnFavoriteEventAddedRemovedListener(onFavoriteEventAddedRemovedListener: OnFavoriteEventAddedRemovedListener) {
        this.onFavoriteEventAddedRemovedListener = onFavoriteEventAddedRemovedListener
    }

    fun setSportEvents(sportEvents: List<Sport>) {
        this.sports = sportEvents
        notifyDataSetChanged()
    }

    class SportViewHolder(
        private val binding: ItemSportBinding,
        private val onSportExpandedMinimizedListener: OnSportExpandedMinimizedListener,
        private val onSportSortOptionSelectedListener: OnSportSortOptionSelectedListener,
        private val onFavoriteEventAddedRemovedListener: OnFavoriteEventAddedRemovedListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sport: Sport) {
            binding.sportNameText.text = sport.sportName

            binding.favoriteFirstRadioButton.setOnClickListener {
                onSportSortOptionSelectedListener.onSortOptionSelected(
                    sport,
                    SortOption.FAVORITE_FIRST
                )
            }

            binding.endingSoonestFirstRadioButton.setOnClickListener {
                onSportSortOptionSelectedListener.onSortOptionSelected(
                    sport,
                    SortOption.ENDING_SOONEST_FIRST
                )
            }

            when (sport.sortOption) {
                SortOption.ENDING_SOONEST_FIRST -> {
                    binding.sortByRadioGroup.check(R.id.ending_soonest_first_radio_button)
                }

                SortOption.FAVORITE_FIRST -> {
                    binding.sortByRadioGroup.check(R.id.favorite_first_radio_button)
                }
            }

            binding.expandMinimizeButton.setOnClickListener {
                onSportExpandedMinimizedListener.onExpandedMinimized(sport)
            }

            if (sport.isExpanded) {
                binding.expandMinimizeButton.setImageResource(R.drawable.ic_minimize)
                binding.eventRecycler.visibility = View.VISIBLE
            } else {
                binding.expandMinimizeButton.setImageResource(R.drawable.ic_expand)
                binding.eventRecycler.visibility = View.GONE
            }

            binding.eventRecycler.layoutManager = GridLayoutManager(binding.root.context, 4)
            val eventRecyclerAdapter = EventRecyclerAdapter()
            eventRecyclerAdapter.setEvents(sport.events)
            eventRecyclerAdapter.setOnFavoriteEventAddedRemovedListener(
                onFavoriteEventAddedRemovedListener
            )
            binding.eventRecycler.adapter = eventRecyclerAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportViewHolder {
        return SportViewHolder(
            ItemSportBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onSportExpandedMinimizedListener,
            onSportSortOptionSelectedListener,
            onFavoriteEventAddedRemovedListener
        )
    }

    override fun getItemCount(): Int {
        return sports.size
    }

    override fun onBindViewHolder(holder: SportViewHolder, position: Int) {
        val sport = sports[position]
        holder.bind(sport)
    }
}