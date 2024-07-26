package com.hoangpd15.smartmovie.ui.searchScreen

import androidx.lifecycle.*
import com.hoangpd15.smartmovie.doumain.MovieRepository
import com.hoangpd15.smartmovie.model.Movie
import com.hoangpd15.smartmovie.model.dataRemote.RetrofitInstance
import com.hoangpd15.smartmovie.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private val _noFindMovie = MutableLiveData<Boolean>()
    val noFindMovie: LiveData<Boolean> get() = _noFindMovie

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun searchMovies(query: String) {
        viewModelScope.launch {
            _noFindMovie.postValue(false)
            _uiState.value = UiState.Loading
            try {
                val response = repository.getSearchMovies(query)
                _uiState.value = UiState.Success(response.results)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
                _noFindMovie.postValue(true)
            }
        }
    }
}