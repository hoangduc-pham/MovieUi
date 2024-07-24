package com.hoangpd15.smartmovie.ui.homeScreen.upComingScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoangpd15.smartmovie.model.dataRemote.RetrofitInstance
import com.hoangpd15.smartmovie.model.Movie
import com.hoangpd15.smartmovie.ui.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpComingViewModel : ViewModel() {
//    private val _upComingMovies = MutableLiveData<List<Movie>>()
//    val upComingMovies: LiveData<List<Movie>> get() = _upComingMovies
//
//    private val _isLoadMore = MutableLiveData<Boolean>()
//    val isLoadMore: LiveData<Boolean> get() = _isLoadMore
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> get() = _isLoading
private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    private var currentPage = 1
    private var currentMovies: List<Movie> = emptyList()

    init {
        fetchUpComingMovies(currentPage)
    }

    fun fetchUpComingMovies(page: Int) {
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
            val upComingMovies = withContext(Dispatchers.IO) {
                RetrofitInstance.apiMovieUpComing.getUpComingMovies(page).results
            }
            currentMovies = (currentMovies + upComingMovies).distinct()
            _uiState.value = UiState.Success(currentMovies)
            currentPage++
        } catch (e: Exception) {
            _uiState.value = UiState.Error(e.message ?: "Unknown error")
        }
    }
}
