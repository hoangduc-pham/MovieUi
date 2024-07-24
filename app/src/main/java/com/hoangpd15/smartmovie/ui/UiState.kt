package com.hoangpd15.smartmovie.ui

sealed class UiState {
    data object Loading : UiState()
    data object LoadMore : UiState()
    data class  Success <out T>(val list: List<T>) : UiState()
    data class Error(val message: String) : UiState()
}