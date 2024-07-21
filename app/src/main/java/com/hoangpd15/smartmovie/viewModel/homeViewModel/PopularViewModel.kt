package com.hoangpd15.smartmovie.viewModel.homeViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoangpd15.smartmovie.model.dataRemote.RetrofitInstance
import com.hoangpd15.smartmovie.model.Movie
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PopularViewModel : ViewModel() {
    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>> get() = _popularMovies

    private val _isLoadMore = MutableLiveData<Boolean>()
    val isLoadMore: LiveData<Boolean> get() = _isLoadMore

    private var currentPage = 1

    init {
        fetchPopularMovies(currentPage)
    }

    private fun fetchPopularMovies(page: Int) {
        viewModelScope.launch {
            loadMoviesFromApi(page)
        }
    }

    fun loadMoreMovies() {
        viewModelScope.launch {
            _isLoadMore.postValue(true)
            // Delay 2 giây trước khi thực hiện tải dữ liệu
            delay(1000) // 2000 milliseconds = 2 seconds
            loadMoviesFromApi(currentPage)
        }
    }
    private suspend fun loadMoviesFromApi(page: Int) {
        try {
            val popularMovies = RetrofitInstance.apiMoviePopular.getPopularMovies(page).results
            val currentList = _popularMovies.value?.toMutableList() ?: mutableListOf()
            currentList.addAll(popularMovies)
            _popularMovies.postValue(currentList)
            currentPage++
        } catch (e: Exception) {
            _popularMovies.postValue(emptyList())
        } finally {
            _isLoadMore.postValue(false)
        }
    }
}
