package com.hoangpd15.smartmovie.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoangpd15.smartmovie.model.Movie
import com.hoangpd15.smartmovie.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    private var currentPage = 1
    private var currentMovies: List<Movie> = emptyList()

    fun fetchMovies(page: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            loadMoviesFromApi(page)
        }
    }

    fun loadMoreMovies() {
        viewModelScope.launch {
            _uiState.value = UiState.LoadMore
            delay(1000)
            loadMoviesFromApi(currentPage)
        }
    }

    private suspend fun loadMoviesFromApi(page: Int) {
        try {
            val movies = fetchMoviesFromApi(page)
            currentMovies = (currentMovies + movies).distinct()
            _uiState.value = UiState.Success(currentMovies)
            currentPage++
        } catch (e: Exception) {
            _uiState.value = UiState.Error(e.message ?: "Unknown error")
        }
    }

     open suspend fun fetchMoviesFromApi(page: Int): List<Movie> = emptyList()
}
