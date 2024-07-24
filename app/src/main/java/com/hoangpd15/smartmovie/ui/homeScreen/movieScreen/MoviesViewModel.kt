package com.hoangpd15.smartmovie.ui.homeScreen.movieScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoangpd15.smartmovie.model.Movie
import com.hoangpd15.smartmovie.model.dataRemote.RetrofitInstance
import com.hoangpd15.smartmovie.ui.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesViewModel : ViewModel() {
    private val popularMoviesApi = RetrofitInstance.apiMoviePopular
    private val topRateMoviesApi = RetrofitInstance.apiMovieTopRate
    private val nowPlayingMoviesApi = RetrofitInstance.apiMovieNowPlaying
    private val upComingMoviesApi = RetrofitInstance.apiMovieUpComing

    //    val popularMovies = MutableLiveData<List<Movie>>()
//    val topRatedMovies = MutableLiveData<List<Movie>>()
//    val nowPlayingMovies = MutableLiveData<List<Movie>>()
//    val upComingMovies = MutableLiveData<List<Movie>>()
//    private val errorMessage = MutableLiveData<String>()
//
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> get() = _isLoading
//
    private val _textPopular = MutableLiveData<Boolean>()
    val textPopular: LiveData<Boolean> get() = _textPopular

    private val _textTopRated = MutableLiveData<Boolean>()
    val textTopRated: LiveData<Boolean> get() = _textTopRated

    private val _textNowPlaying = MutableLiveData<Boolean>()
    val textNowPlaying: LiveData<Boolean> get() = _textNowPlaying

    private val _textUpComing = MutableLiveData<Boolean>()
    val textUpComing: LiveData<Boolean> get() = _textUpComing

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

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
            _uiState.value = UiState.Loading
            try {
                val movies = popularMoviesApi.getPopularMovies(1).results
                _uiState.value = UiState.Success(movies)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            } finally {
                _textPopular.postValue(true)
            }
        }
    }

    private fun fetchTopRatedMovies() {
        viewModelScope.launch {
            _textTopRated.postValue(false)
            _uiState.value = UiState.Loading
            try {
                val movies = topRateMoviesApi.getTopRateMovies(1).results
                _uiState.value = UiState.Success(movies)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            } finally {
                _textTopRated.postValue(true)
            }
        }
    }

    private fun fetchUpComingMovies() {
        viewModelScope.launch {
            _textUpComing.postValue(false)
            _uiState.value = UiState.Loading
            try {
                val movies = withContext(Dispatchers.IO) {
                    upComingMoviesApi.getUpComingMovies(1).results
                }
                _uiState.value = UiState.Success(movies)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            } finally {
                _textUpComing.postValue(true)
            }
        }
    }

    private fun fetchNowPlayingMovies() {
        viewModelScope.launch {
            _textNowPlaying.postValue(false)
            _uiState.value = UiState.Loading
            try {
                val movies = withContext(Dispatchers.IO) {
                    nowPlayingMoviesApi.getNowPlayingMovies(1).results
                }
                _uiState.value = UiState.Success(movies)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            } finally {
                _textNowPlaying.postValue(true)
            }
        }
    }
}
