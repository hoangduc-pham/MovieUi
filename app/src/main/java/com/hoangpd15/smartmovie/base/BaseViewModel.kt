package com.hoangpd15.smartmovie.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.DeleteFavoriteMovieUseCase
import com.example.domain.InsertFavoriteMovieUseCase
import com.example.domain.entities.FavoriteMovieEntity
import com.example.domain.entities.Movie
import com.hoangpd15.smartmovie.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
    private val insertFavoriteMovieUseCase: InsertFavoriteMovieUseCase,
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    private var currentPage = 1
    private var currentMovies: List<Movie> = emptyList()

    init {
        fetchMovies(1)
    }
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

    fun insertFavoriteMovie(movie: FavoriteMovieEntity) {
        viewModelScope.launch {
            insertFavoriteMovieUseCase(movie)
        }
    }

    fun deleteFavoriteMovie(movieId: Int) {
        viewModelScope.launch {
            deleteFavoriteMovieUseCase(movieId)
        }
    }

    open suspend fun fetchMoviesFromApi(page: Int): List<Movie> = emptyList()
}
