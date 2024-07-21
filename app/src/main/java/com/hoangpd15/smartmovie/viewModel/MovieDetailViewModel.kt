package com.hoangpd15.smartmovie.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoangpd15.smartmovie.model.Cast
import com.hoangpd15.smartmovie.model.GenresMovie
import com.hoangpd15.smartmovie.model.Language
import com.hoangpd15.smartmovie.model.MovieDetailResponse
import com.hoangpd15.smartmovie.model.dataRemote.RetrofitInstance
import kotlinx.coroutines.launch

class MovieDetailViewModel : ViewModel() {

    private val _movieDetail = MutableLiveData<MovieDetailResponse>()
    private val _language = MutableLiveData<List<Language>>()
    private val _movieDetailGenres = MutableLiveData<List<GenresMovie>>()
    val movieDetail: LiveData<MovieDetailResponse> get() = _movieDetail
    val movieDetailGenres: LiveData<List<GenresMovie>> get() = _movieDetailGenres
    val language: LiveData<List<Language>> get() = _language

    private val _castMovie = MutableLiveData<List<Cast>>()
    val castMovie: LiveData<List<Cast>> get() = _castMovie

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    fun fetchMovieDetail(movieId: Int) {
        viewModelScope.launch {
            try {
                val movieDetailResponse = RetrofitInstance.apiMovieDetail.getMovieDetails(movieId)
                _movieDetail.postValue(movieDetailResponse)
                _language.postValue(movieDetailResponse.spokenLanguages)
                _movieDetailGenres.postValue(movieDetailResponse.genres)

                val castResponse = RetrofitInstance.apiCastMovie.getCastMovie(movieId)
                _castMovie.postValue(castResponse.cast)
                _isLoading.value = false
            } catch (e: Exception) {
                Log.e("MovieDetailViewModel", "Error fetching movie details: ${e.message}")
            }
        }
    }
}