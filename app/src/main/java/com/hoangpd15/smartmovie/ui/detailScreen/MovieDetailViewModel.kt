package com.hoangpd15.smartmovie.ui.detailScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.dataRemote.RetrofitInstance
import com.example.domain.CastMovieUseCase
import com.example.domain.MovieDetailUseCase
import com.example.domain.entities.Cast
import com.example.domain.entities.GenresMovie
import com.example.domain.entities.Language
import com.example.domain.entities.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailUseCase : MovieDetailUseCase,
    private val castMovieUseCase : CastMovieUseCase,
) : ViewModel() {

    private val _movieDetail = MutableLiveData<MovieDetail>()
    private val _language = MutableLiveData<List<Language>>()
    private val _movieDetailGenres = MutableLiveData<List<GenresMovie>>()
    val movieDetail: LiveData<MovieDetail> get() = _movieDetail
    val movieDetailGenres: LiveData<List<GenresMovie>> get() = _movieDetailGenres
    val language: LiveData<List<Language>> get() = _language

    private val _castMovie = MutableLiveData<List<Cast>>()
    val castMovie: LiveData<List<Cast>> get() = _castMovie

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    fun fetchMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val movieDetailResponse = movieDetailUseCase(movieId)
                _movieDetail.postValue(movieDetailResponse)
                _language.postValue(movieDetailResponse.spokenLanguages)
                _movieDetailGenres.postValue(movieDetailResponse.genres)

                val castResponse = castMovieUseCase(movieId)
                _castMovie.postValue(castResponse.cast)
            } catch (e: Exception) {
//                Log.e("MovieDetailViewModel", "Error fetching movie details: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}