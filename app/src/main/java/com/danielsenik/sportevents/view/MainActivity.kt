package com.danielsenik.sportevents.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.danielsenik.sportevents.model.ui.Event
import com.danielsenik.sportevents.view.listener.OnSportExpandedMinimizedListener
import com.danielsenik.sportevents.model.util.Resource
import com.danielsenik.sportevents.model.ui.Sport
import com.danielsenik.sportevents.view.adapter.SportRecyclerAdapter
import com.danielsenik.sportevents.databinding.ActivityMainBinding
import com.danielsenik.sportevents.model.enumerator.SortOption
import com.danielsenik.sportevents.view.listener.OnFavoriteEventAddedRemovedListener
import com.danielsenik.sportevents.view.listener.OnSportSortOptionSelectedListener
import com.danielsenik.sportevents.viewmodel.SportViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var sportRecyclerAdapter: SportRecyclerAdapter

    private lateinit var sportViewModel: SportViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.refreshButton.setOnClickListener {
            sportViewModel.getSports()
        }

        sportRecyclerAdapter = SportRecyclerAdapter()
        sportRecyclerAdapter.setOnExpandedMinimizedListener(object :
            OnSportExpandedMinimizedListener {
            override fun onExpandedMinimized(sport: Sport) {
                sportViewModel.expandMinimizeSport(sport)
            }
        })
        sportRecyclerAdapter.setOnSportSortOptionSelectedListener(object :
            OnSportSortOptionSelectedListener {
            override fun onSortOptionSelected(sport: Sport, sortOption: SortOption) {
                sportViewModel.sortSportEvents(sport, sortOption)
            }

        })
        sportRecyclerAdapter.setOnFavoriteEventAddedRemovedListener(object :
            OnFavoriteEventAddedRemovedListener {
            override fun onAddedRemoved(event: Event) {
                sportViewModel.addRemoveFavoriteEvent(event)
            }
        })

        binding.sportRecycler.adapter = sportRecyclerAdapter

        sportViewModel = ViewModelProvider(this).get(SportViewModel::class.java)

        sportViewModel.getObservableSports().observe(this) {
            when (it) {
                is Resource.OK -> {
                    setLoading(false)

                    if (it.data == null) {
                        showToast("No Sports")
                    } else {
                        setSports(it.data)
                    }
                }

                is Resource.LOADING -> {
                    setLoading(true)
                }

                is Resource.ERROR -> {
                    setLoading(false)
                    showToast(it.error.toString())
                }
            }
        }
    }

    private fun setSports(sports: List<Sport>) {
        sportRecyclerAdapter.setSportEvents(sports)
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.refreshButton.isEnabled = false
            binding.sportRecycler.visibility = View.GONE
            binding.loadingProgress.visibility = View.VISIBLE
        } else {
            binding.refreshButton.isEnabled = true
            binding.sportRecycler.visibility = View.VISIBLE
            binding.loadingProgress.visibility = View.GONE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}