package com.jicsoftwarestudio.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jicsoftwarestudio.model.dataRemote.RetrofitInstance
import com.jicsoftwarestudio.model.Movie
import kotlinx.coroutines.launch

class PopularViewModel : ViewModel() {
    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>> get() = _popularMovies

    init {
        fetchPopularMovies()
    }

    fun fetchPopularMovies() {
        viewModelScope.launch {
            try {
                val popularMovies = RetrofitInstance.apiMoviePopular.getPopularMovies().results
                _popularMovies.postValue(popularMovies)
            } catch (e: Exception) {
                // Handle error
                _popularMovies.postValue(emptyList())
            }
        }
    }
}