package com.hoangpd15.smartmovie.ui.homeScreen.movieScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoangpd15.smartmovie.model.Movie
import com.hoangpd15.smartmovie.model.dataRemote.RetrofitInstance
import com.hoangpd15.smartmovie.ui.UiState
import com.hoangpd15.smartmovie.ui.UiStateAllMovie
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

    private val _uiStatePopular = MutableLiveData<UiStateAllMovie>()
    val uiStatePopular: LiveData<UiStateAllMovie> get() = _uiStatePopular

    private val _uiStateTopRated = MutableLiveData<UiStateAllMovie>()
    val uiStateTopRated: LiveData<UiStateAllMovie> get() = _uiStateTopRated

    private val _uiStateNowPlaying = MutableLiveData<UiStateAllMovie>()
    val uiStateNowPlaying: LiveData<UiStateAllMovie> get() = _uiStateNowPlaying

    private val _uiStateUpComing = MutableLiveData<UiStateAllMovie>()
    val uiStateUpComing: LiveData<UiStateAllMovie> get() = _uiStateUpComing


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
            _uiStatePopular.value = UiStateAllMovie.Loading
            try {
                val movies = popularMoviesApi.getPopularMovies(1).results
                _uiStatePopular.value = UiStateAllMovie.Success(movies, UiStateAllMovie.MovieType.POPULAR)
            } catch (e: Exception) {
                _uiStatePopular.value = UiStateAllMovie.Error(e.message ?: "Unknown error")
            } finally {
                _textPopular.postValue(true)
            }
        }
    }

    private fun fetchTopRatedMovies() {
        viewModelScope.launch {
            _textTopRated.postValue(false)
            _uiStateTopRated.value = UiStateAllMovie.Loading
            try {
                val movies = topRateMoviesApi.getTopRateMovies(1).results
                _uiStateTopRated.value = UiStateAllMovie.Success(movies, UiStateAllMovie.MovieType.TOP_RATED)
            } catch (e: Exception) {
                _uiStateTopRated.value = UiStateAllMovie.Error(e.message ?: "Unknown error")
            } finally {
                _textTopRated.postValue(true)
            }
        }
    }

    private fun fetchUpComingMovies() {
        viewModelScope.launch {
            _textUpComing.postValue(false)
            _uiStateUpComing.value = UiStateAllMovie.Loading
            try {
                val movies = upComingMoviesApi.getUpComingMovies(1).results
                _uiStateUpComing.value = UiStateAllMovie.Success(movies, UiStateAllMovie.MovieType.UPCOMING)
            } catch (e: Exception) {
                _uiStateUpComing.value = UiStateAllMovie.Error(e.message ?: "Unknown error")
            } finally {
                _textUpComing.postValue(true)
            }
        }
    }

    private fun fetchNowPlayingMovies() {
        viewModelScope.launch {
            _textNowPlaying.postValue(false)
            _uiStateNowPlaying.value = UiStateAllMovie.Loading
            try {
                val movies =
                    nowPlayingMoviesApi.getNowPlayingMovies(1).results
                _uiStateNowPlaying.value = UiStateAllMovie.Success(movies, UiStateAllMovie.MovieType.NOW_PLAYING)
            } catch (e: Exception) {
                _uiStateNowPlaying.value = UiStateAllMovie.Error(e.message ?: "Unknown error")
            } finally {
                _textNowPlaying.postValue(true)
            }
        }
    }
}
