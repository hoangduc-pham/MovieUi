package com.hoangpd15.smartmovie.ui.homeScreen.upComingScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoangpd15.smartmovie.model.dataRemote.RetrofitInstance
import com.hoangpd15.smartmovie.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpComingViewModel : ViewModel() {
    private val _upComingMovies = MutableLiveData<List<Movie>>()
    val upComingMovies: LiveData<List<Movie>> get() = _upComingMovies

    private val _isLoadMore = MutableLiveData<Boolean>()
    val isLoadMore: LiveData<Boolean> get() = _isLoadMore

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var currentPage = 1

    init {
        fetchUpComingMovies(currentPage)
    }

    fun fetchUpComingMovies(page: Int) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            loadMoviesFromApi(page)
        }
    }

    fun loadMoreMovies() {
        viewModelScope.launch {
            _isLoadMore.postValue(true)
            delay(1000)
            loadMoviesFromApi(currentPage)
        }
    }

    private suspend fun loadMoviesFromApi(page: Int) {
        try {
            val upComingMovies = withContext(Dispatchers.IO) {
                RetrofitInstance.apiMovieUpComing.getUpComingMovies(page).results
            }
            val currentList = _upComingMovies.value?.toMutableList() ?: mutableListOf()
            currentList.addAll(upComingMovies)
            _upComingMovies.postValue(currentList)
            currentPage++
        } catch (e: Exception) {
            _upComingMovies.postValue(emptyList())
        } finally {
            _isLoading.postValue(false)
            _isLoadMore.postValue(false)
        }
    }
}
