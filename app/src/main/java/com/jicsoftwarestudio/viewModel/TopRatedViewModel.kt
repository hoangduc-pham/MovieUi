package com.jicsoftwarestudio.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jicsoftwarestudio.model.dataRemote.RetrofitInstance
import com.jicsoftwarestudio.model.Movie
import kotlinx.coroutines.launch

class TopRatedViewModel : ViewModel() {
    private val _topRatedMovies = MutableLiveData<List<Movie>>()
    val topRatedMovies: LiveData<List<Movie>> get() = _topRatedMovies

    init {
        fetchTopRatedMovies()
    }

    fun fetchTopRatedMovies() {
        viewModelScope.launch {
            try {
                val topRatedMovies = RetrofitInstance.apiMovieTopRate.getTopRateMovies().results
                _topRatedMovies.postValue(topRatedMovies)
            } catch (e: Exception) {
                // Handle error
                _topRatedMovies.postValue(emptyList())
            }
        }
    }
}