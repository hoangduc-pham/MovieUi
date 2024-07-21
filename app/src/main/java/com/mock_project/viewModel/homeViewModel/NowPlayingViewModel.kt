package com.mock_project.viewModel.homeViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mock_project.model.dataRemote.RetrofitInstance
import com.mock_project.model.Movie
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NowPlayingViewModel : ViewModel() {
    private val _nowPlayingMovies = MutableLiveData<List<Movie>>()
    val nowPlayingMovies: LiveData<List<Movie>> get() = _nowPlayingMovies

    private val _isLoadMore = MutableLiveData<Boolean>()
    val isLoadMore: LiveData<Boolean> get() = _isLoadMore

    private var currentPage = 1

    init {
        fetchNowPlayingMovies(currentPage)
    }

    private fun fetchNowPlayingMovies(page: Int) {
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
            val nowPlayingMovies = RetrofitInstance.apiMovieNowPlaying.getNowPlayingMovies(page).results
            val currentList = _nowPlayingMovies.value?.toMutableList() ?: mutableListOf()
            currentList.addAll(nowPlayingMovies)
            _nowPlayingMovies.postValue(currentList)
            currentPage++
        } catch (e: Exception) {
            _nowPlayingMovies.postValue(emptyList())
        } finally {
            _isLoadMore.postValue(false)
        }
    }
}