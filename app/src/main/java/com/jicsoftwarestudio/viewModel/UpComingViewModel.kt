package com.jicsoftwarestudio.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jicsoftwarestudio.model.dataRemote.RetrofitInstance
import com.jicsoftwarestudio.model.Movie
import kotlinx.coroutines.launch

class UpComingViewModel : ViewModel() {
    private val _upComingMovies = MutableLiveData<List<Movie>>()
    val upComingMovies: LiveData<List<Movie>> get() = _upComingMovies

    init {
        fetchUpComingMovies()
    }

    fun fetchUpComingMovies() {
        viewModelScope.launch {
            try {
                val upComingMovies = RetrofitInstance.apiMovieUpComing.getUpComingMovies().results
                _upComingMovies.postValue(upComingMovies)
            } catch (e: Exception) {
                // Handle error
                _upComingMovies.postValue(emptyList())
            }
        }
    }
}