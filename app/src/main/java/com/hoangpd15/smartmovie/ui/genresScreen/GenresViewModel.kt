package com.hoangpd15.smartmovie.ui.genresScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoangpd15.smartmovie.doumain.MovieRepository
import com.hoangpd15.smartmovie.model.Genres
import com.hoangpd15.smartmovie.model.dataRemote.RetrofitInstance
import com.hoangpd15.smartmovie.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    init {
        fetchListGenres()
    }

    private fun fetchListGenres() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val listGenres = repository.getListGenres().genres
                _uiState.value = UiState.Success(listGenres)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}