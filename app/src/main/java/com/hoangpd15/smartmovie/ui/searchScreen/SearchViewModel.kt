package com.hoangpd15.smartmovie.ui.searchScreen

import androidx.lifecycle.*
import com.hoangpd15.smartmovie.model.Movie
import com.hoangpd15.smartmovie.model.dataRemote.RetrofitInstance
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val _searchMovies = MutableLiveData<List<Movie>>()
    val searchMovies: LiveData<List<Movie>> get() = _searchMovies


    private val _noFindMovie = MutableLiveData<Boolean>()
    val noFindMovie: LiveData<Boolean> get() = _noFindMovie

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun searchMovies(query: String) {
        viewModelScope.launch {
            _noFindMovie.postValue(false)
            _isLoading.postValue(true)
            try {
                val response = RetrofitInstance.apiMovieSearch.searchMovies(query)
                _searchMovies.postValue(response.results)
            } catch (e: Exception) {
                _searchMovies.postValue(emptyList())
                _noFindMovie.postValue(true)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}