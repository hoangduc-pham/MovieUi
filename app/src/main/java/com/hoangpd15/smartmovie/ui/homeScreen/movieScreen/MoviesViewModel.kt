package com.hoangpd15.smartmovie.ui.homeScreen.movieScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoangpd15.smartmovie.model.Movie
import com.hoangpd15.smartmovie.model.dataRemote.RetrofitInstance
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

    private val _textPopular = MutableLiveData<Boolean>()
    val textPopular: LiveData<Boolean> get() = _textPopular

    private val _textTopRated = MutableLiveData<Boolean>()
    val textTopRated: LiveData<Boolean> get() = _textTopRated

    private val _textNowPlaying = MutableLiveData<Boolean>()
    val textNowPlaying: LiveData<Boolean> get() = _textNowPlaying

    private val _textUpComing = MutableLiveData<Boolean>()
    val textUpComing: LiveData<Boolean> get() = _textUpComing

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
            _textPopular.postValue(false)
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
                _textPopular.postValue(true)
            }
        }
    }

    private fun fetchTopRatedMovies() {
        viewModelScope.launch {
            _textTopRated.postValue(false)
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
                _textTopRated.postValue(true)
            }
        }
    }

    private fun fetchUpComingMovies() {
        viewModelScope.launch {
            _textUpComing.postValue(false)
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
                _textUpComing.postValue(true)
            }
        }
    }

    private fun fetchNowPlayingMovies() {
        viewModelScope.launch {
            _textNowPlaying.postValue(false)
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
                _textNowPlaying.postValue(true)
            }
        }
    }
}
