package com.hoangpd15.smartmovie.ui.homeScreen.popularScreen

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

class PopularViewModel : ViewModel() {
    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>> get() = _popularMovies

    private val _isLoadMore = MutableLiveData<Boolean>()
    val isLoadMore: LiveData<Boolean> get() = _isLoadMore


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var currentPage = 1

    init {
        fetchPopularMovies(currentPage)
    }

    fun fetchPopularMovies(page: Int) {
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
            val popularMovies = withContext(Dispatchers.IO) {
                RetrofitInstance.apiMoviePopular.getPopularMovies(page).results
            }
            val currentList = _popularMovies.value?.toMutableList() ?: mutableListOf()
            currentList.addAll(popularMovies)
            _popularMovies.postValue(currentList)
            currentPage++
        } catch (e: Exception) {
            _popularMovies.postValue(emptyList())
        } finally {
            _isLoadMore.postValue(false)
            _isLoading.postValue(false)
        }
    }
}
