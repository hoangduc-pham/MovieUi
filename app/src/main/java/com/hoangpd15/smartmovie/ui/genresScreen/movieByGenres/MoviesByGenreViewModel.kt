package com.hoangpd15.smartmovie.ui.genresScreen.movieByGenres

import androidx.lifecycle.*
import com.hoangpd15.smartmovie.model.Movie
import com.hoangpd15.smartmovie.model.dataRemote.RetrofitInstance
import com.hoangpd15.smartmovie.ui.UiState
import kotlinx.coroutines.launch

class MoviesByGenreViewModel : ViewModel() {
    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun fetchMoviesByGenre(genreId: Int) {
        viewModelScope.launch {
           _uiState.value = UiState.Loading
            try {
                val response = RetrofitInstance.apiAllMovie.getMoviesByGenre(genreId)
                _uiState.value = UiState.Success(response.results)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
