package com.hoangpd15.smartmovie.ui.homeScreen.nowPlayingScreen

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

class NowPlayingViewModel : ViewModel() {
    private val _nowPlayingMovies = MutableLiveData<List<Movie>>()
    val nowPlayingMovies: LiveData<List<Movie>> get() = _nowPlayingMovies

    private val _isLoadMore = MutableLiveData<Boolean>()
    val isLoadMore: LiveData<Boolean> get() = _isLoadMore

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var currentPage = 1

    init {
        fetchNowPlayingMovies(currentPage)
    }

    fun fetchNowPlayingMovies(page: Int) {
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
            val nowPlayingMovies = withContext(Dispatchers.IO){
                RetrofitInstance.apiMovieNowPlaying.getNowPlayingMovies(page).results
            }
            val currentList = _nowPlayingMovies.value?.toMutableList() ?: mutableListOf()
            currentList.addAll(nowPlayingMovies)
            _nowPlayingMovies.postValue(currentList)
            currentPage++
        } catch (e: Exception) {
            _nowPlayingMovies.postValue(emptyList())
        } finally {
            _isLoading.postValue(false)
            _isLoadMore.postValue(false)
        }
    }
}