package com.jicsoftwarestudio.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jicsoftwarestudio.model.dataRemote.RetrofitInstance
import com.jicsoftwarestudio.model.Movie
import kotlinx.coroutines.launch

class NowPlayingViewModel : ViewModel() {
    private val _nowPlayingMovies = MutableLiveData<List<Movie>>()
    val nowPlayingMovies: LiveData<List<Movie>> get() = _nowPlayingMovies

    init {
        fetchNowPlayingMovies()
    }

    fun fetchNowPlayingMovies() {
        viewModelScope.launch {
            try {
                val nowPlayingMovies = RetrofitInstance.apiMovieNowPlaying.getNowPlayingMovies().results
                _nowPlayingMovies.postValue(nowPlayingMovies)
            } catch (e: Exception) {
                // Handle error
                _nowPlayingMovies.postValue(emptyList())
            }
        }
    }
}