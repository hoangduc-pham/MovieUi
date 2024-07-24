package com.hoangpd15.smartmovie.ui.homeScreen.popularScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoangpd15.smartmovie.model.dataRemote.RetrofitInstance
import com.hoangpd15.smartmovie.model.Movie
import com.hoangpd15.smartmovie.ui.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PopularViewModel : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    private var currentPage = 1
    private var currentMovies: List<Movie> = emptyList()

    init {
        fetchPopularMovies(currentPage)
    }

    fun fetchPopularMovies(page: Int) {
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
            val popularMovies = withContext(Dispatchers.IO) {
                RetrofitInstance.apiMoviePopular.getPopularMovies(page).results
            }
            currentMovies = (currentMovies + popularMovies).distinct()
            _uiState.value =  UiState.Success(currentMovies)
            currentPage++
        } catch (e: Exception) {
            _uiState.value = UiState.Error(e.message ?: "Unknown error")
        }
    }
}
