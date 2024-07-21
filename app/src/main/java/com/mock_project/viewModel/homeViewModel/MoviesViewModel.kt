package com.mock_project.viewModel.homeViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mock_project.model.Movie
import com.mock_project.model.dataRemote.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesViewModel : ViewModel() {
    private val popularMoviesApi = RetrofitInstance.apiMoviePopular
    private val topRateMoviesApi = RetrofitInstance.apiMovieTopRate
    private val nowPlayingMoviesApi = RetrofitInstance.apiMovieNowPlaying
    private val upComingMoviesApi = RetrofitInstance.apiMovieUpComing

    val popularMovies = MutableLiveData<List<Movie>>()
    val topRatedMovies = MutableLiveData<List<Movie>>()
    val nowPlayingMovies = MutableLiveData<List<Movie>>()
    val upComingMovies = MutableLiveData<List<Movie>>()
    private val errorMessage = MutableLiveData<String>()


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun refreshMovies() {
        fetchPopularMovies()
        fetchTopRatedMovies()
        fetchUpComingMovies()
        fetchNowPlayingMovies()
    }
    init {
        refreshMovies()
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val movies = withContext(Dispatchers.IO) {
                    popularMoviesApi.getPopularMovies(1).results
                }
                popularMovies.postValue(movies)
            } catch (e: Exception) {
                errorMessage.postValue("Failed")
            }finally {
                _isLoading.postValue(false)
            }
        }
    }

    private fun fetchTopRatedMovies() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val movies = withContext(Dispatchers.IO) {
                    topRateMoviesApi.getTopRateMovies(1).results
                }
                topRatedMovies.postValue(movies)
            } catch (e: Exception) {
                errorMessage.postValue("Failed")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    private fun fetchUpComingMovies() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val movies = withContext(Dispatchers.IO) {
                    upComingMoviesApi.getUpComingMovies(1).results
                }
                upComingMovies.postValue(movies)
            } catch (e: Exception) {
                errorMessage.postValue("Failed to fetch now playing movies: ${e.message}")
            }finally {
                _isLoading.postValue(false)
            }
        }
    }

    private fun fetchNowPlayingMovies() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val movies = withContext(Dispatchers.IO) {
                    nowPlayingMoviesApi.getNowPlayingMovies(1).results
                }
                nowPlayingMovies.postValue(movies)
            } catch (e: Exception) {
                errorMessage.postValue("Failed to fetch now playing movies: ${e.message}")
            }finally {
                _isLoading.postValue(false)
            }
        }
    }
}
