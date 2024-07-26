package com.hoangpd15.smartmovie.ui.homeScreen.movieScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoangpd15.smartmovie.doumain.MovieRepository
import com.hoangpd15.smartmovie.model.Movie
import com.hoangpd15.smartmovie.model.dataRemote.RetrofitInstance
import com.hoangpd15.smartmovie.ui.UiState
import com.hoangpd15.smartmovie.ui.UiStateAllMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
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
                val movies = repository.getPopularMovies(1).results
                _uiStatePopular.value = UiStateAllMovie.Success(movies, UiStateAllMovie.MovieType.POPULAR)
                _textPopular.postValue(true)
            } catch (e: Exception) {
                _uiStatePopular.value = UiStateAllMovie.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun fetchTopRatedMovies() {
        viewModelScope.launch {
            _textTopRated.postValue(false)
            _uiStateTopRated.value = UiStateAllMovie.Loading
            try {
                val movies = repository.getTopRateMovies(1).results
                _uiStateTopRated.value = UiStateAllMovie.Success(movies, UiStateAllMovie.MovieType.TOP_RATED)
                _textTopRated.postValue(true)
            } catch (e: Exception) {
                _uiStateTopRated.value = UiStateAllMovie.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun fetchUpComingMovies() {
        viewModelScope.launch {
            _textUpComing.postValue(false)
            _uiStateUpComing.value = UiStateAllMovie.Loading
            try {
                val movies = repository.getUpComingMovies(1).results
                _uiStateUpComing.value = UiStateAllMovie.Success(movies, UiStateAllMovie.MovieType.UPCOMING)
                _textUpComing.postValue(true)
            } catch (e: Exception) {
                _uiStateUpComing.value = UiStateAllMovie.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun fetchNowPlayingMovies() {
        viewModelScope.launch {
            _textNowPlaying.postValue(false)
            _uiStateNowPlaying.value = UiStateAllMovie.Loading
            try {
                val movies =
                    repository.getNowPlayingMovies(1).results
                _uiStateNowPlaying.value = UiStateAllMovie.Success(movies, UiStateAllMovie.MovieType.NOW_PLAYING)
                _textNowPlaying.postValue(true)
            } catch (e: Exception) {
                _uiStateNowPlaying.value = UiStateAllMovie.Error(e.message ?: "Unknown error")
            }
        }
    }
}
