package com.hoangpd15.smartmovie.ui.homeScreen.topRatedScreen

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

class TopRatedViewModel : ViewModel() {
    private val _topRatedMovies = MutableLiveData<List<Movie>>()
    val topRatedMovies: LiveData<List<Movie>> get() = _topRatedMovies

    private val _isLoadMore = MutableLiveData<Boolean>()
    val isLoadMore: LiveData<Boolean> get() = _isLoadMore

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var currentPage = 1

    init {
        fetchTopRatedMovies(currentPage)
    }

    fun fetchTopRatedMovies(page: Int) {
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
            val topRatedMovies = withContext(Dispatchers.IO) {
                RetrofitInstance.apiMovieTopRate.getTopRateMovies(page).results
            }
            val currentList = _topRatedMovies.value?.toMutableList() ?: mutableListOf()
            currentList.addAll(topRatedMovies)
            _topRatedMovies.postValue(currentList)
            currentPage++
        } catch (e: Exception) {
            _topRatedMovies.postValue(emptyList())
        } finally {
            _isLoading.postValue(false)
            _isLoadMore.postValue(false)
        }
    }
}