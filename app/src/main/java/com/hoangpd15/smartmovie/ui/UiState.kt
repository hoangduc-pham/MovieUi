package com.hoangpd15.smartmovie.ui

import com.example.domain.entities.Movie

sealed class UiState {
    data object Loading : UiState()
    data object LoadMore : UiState()
    data class Success<out T>(val list: List<T>) : UiState()
    data class Error(val message: String) : UiState()
}

sealed class UiStateAllMovie {
    data object Loading : UiStateAllMovie()
    data class Success(val list: List<Movie>, val type: MovieType) : UiStateAllMovie()
    data class Error(val message: String) : UiStateAllMovie()

    enum class MovieType {
        POPULAR,
        TOP_RATED,
        NOW_PLAYING,
        UPCOMING
    }
}