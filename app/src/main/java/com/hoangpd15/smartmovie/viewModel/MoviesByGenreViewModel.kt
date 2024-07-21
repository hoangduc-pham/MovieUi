package com.hoangpd15.smartmovie.viewModel

import androidx.lifecycle.*
import com.hoangpd15.smartmovie.model.Movie
import com.hoangpd15.smartmovie.model.dataRemote.RetrofitInstance
import kotlinx.coroutines.launch

class MoviesByGenreViewModel : ViewModel() {
    private val _moviesByGenre = MutableLiveData<List<Movie>>()
    val moviesByGenre: LiveData<List<Movie>> get() = _moviesByGenre

    fun fetchMoviesByGenre(genreId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.apiAllMovie.getMoviesByGenre(genreId)
                _moviesByGenre.postValue(response.results)
            } catch (e: Exception) {
                _moviesByGenre.postValue(emptyList())
            }
        }
    }
}
