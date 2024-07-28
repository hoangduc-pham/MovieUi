package com.hoangpd15.smartmovie.ui.genresScreen.movieByGenres

import androidx.lifecycle.*
import com.example.domain.GetMoviesByGenreUseCase
import com.hoangpd15.smartmovie.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesByGenreViewModel @Inject constructor(private val genresMovieUseCase: GetMoviesByGenreUseCase) : ViewModel() {
    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun fetchMoviesByGenre(genreId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = genresMovieUseCase(genreId)
                _uiState.value = UiState.Success(response.results)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
