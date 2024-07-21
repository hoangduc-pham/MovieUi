package com.hoangpd15.smartmovie.viewModel.homeViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoangpd15.smartmovie.model.dataRemote.RetrofitInstance
import com.hoangpd15.smartmovie.model.Movie
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UpComingViewModel : ViewModel() {
    private val _upComingMovies = MutableLiveData<List<Movie>>()
    val upComingMovies: LiveData<List<Movie>> get() = _upComingMovies

    private val _isLoadMore = MutableLiveData<Boolean>()
    val isLoadMore: LiveData<Boolean> get() = _isLoadMore

    private var currentPage = 1

    init {
        fetchUpComingMovies(currentPage)
    }

    private fun fetchUpComingMovies(page: Int) {
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
            val upComingMovies = RetrofitInstance.apiMovieUpComing.getUpComingMovies(page).results
            val currentList = _upComingMovies.value?.toMutableList() ?: mutableListOf()
            currentList.addAll(upComingMovies)
            _upComingMovies.postValue(currentList)
            currentPage++
        } catch (e: Exception) {
            _upComingMovies.postValue(emptyList())
        } finally {
            _isLoadMore.postValue(false)
        }
    }
}
