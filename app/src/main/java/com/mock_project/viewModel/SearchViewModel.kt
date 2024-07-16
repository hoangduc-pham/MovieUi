package com.mock_project.viewModel

import androidx.lifecycle.*
import com.mock_project.model.Movie
import com.mock_project.model.dataRemote.RetrofitInstance
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val _searchMovies = MutableLiveData<List<Movie>>()
    val searchMovies: LiveData<List<Movie>> get() = _searchMovies


    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.apiMovieSearch.searchMovies(query)
                _searchMovies.postValue(response.results)
            } catch (e: Exception) {
                _searchMovies.postValue(emptyList())
            }
        }
    }
}