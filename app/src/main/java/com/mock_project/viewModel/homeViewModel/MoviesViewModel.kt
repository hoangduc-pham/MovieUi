package com.mock_project.viewModel.homeViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mock_project.model.Movie
import com.mock_project.model.dataRemote.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesViewModel : ViewModel() {
    val popularMoviesApi = RetrofitInstance.apiMoviePopular
    val topRateMoviesApi = RetrofitInstance.apiMovieTopRate
    private val upComingMoviesApi = RetrofitInstance.apiMovieUpComing
    private val nowPlayingMoviesApi = RetrofitInstance.apiMovieNowPlaying

    val popularMovies = MutableLiveData<List<Movie>>()
    val topRatedMovies = MutableLiveData<List<Movie>>()
    val upcomingMovies = MutableLiveData<List<Movie>>()
    val nowPlayingMovies = MutableLiveData<List<Movie>>()
    private val errorMessage = MutableLiveData<String>()

    init {
        fetchPopularMovies()
        fetchTopRatedMovies()
        fetchUpcomingMovies()
        fetchNowPlayingMovies()
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            try {
                val movies = withContext(Dispatchers.IO) {
                    popularMoviesApi.getPopularMovies().results
                }
                popularMovies.postValue(movies)
            } catch (e: Exception) {
                errorMessage.postValue("Failed")
            }
        }
    }

    private fun fetchTopRatedMovies() {
        viewModelScope.launch {
            try {
                val movies = withContext(Dispatchers.IO) {
                    topRateMoviesApi.getTopRateMovies().results
                }
                topRatedMovies.postValue(movies)
            } catch (e: Exception) {
                errorMessage.postValue("Failed")
            }
        }
    }

    private fun fetchUpcomingMovies() {
        viewModelScope.launch {
            try {
                val movies = withContext(Dispatchers.IO) {
                    upComingMoviesApi.getUpComingMovies().results
                }
                upcomingMovies.postValue(movies)
            } catch (e: Exception) {
                errorMessage.postValue("Failed")
            }
        }
    }

    private fun fetchNowPlayingMovies() {
        viewModelScope.launch {
            try {
                val movies = withContext(Dispatchers.IO) {
                    nowPlayingMoviesApi.getNowPlayingMovies().results
                }
                nowPlayingMovies.postValue(movies)
            } catch (e: Exception) {
                errorMessage.postValue("Failed to fetch now playing movies: ${e.message}")
            }
        }
    }
}
