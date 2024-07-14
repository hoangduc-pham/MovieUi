package com.jicsoftwarestudio.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jicsoftwarestudio.model.dataRemote.RetrofitInstance
import kotlinx.coroutines.Dispatchers

class MoviesViewModel : ViewModel() {
    var popularMoviesApi = RetrofitInstance.apiMoviePopular
    var topRateMoviesApi = RetrofitInstance.apiMovieTopRate
    var upComingMoviesApi = RetrofitInstance.apiMovieUpComing
    var nowPlayingMoviesApi = RetrofitInstance.apiMovieNowPlaying

    val popularMovies = liveData(Dispatchers.IO) {
        val movies = popularMoviesApi.getPopularMovies().results
        emit(movies)
    }

    val topRatedMovies = liveData(Dispatchers.IO) {
        val movies = topRateMoviesApi.getTopRateMovies().results
        emit(movies)
    }

    val upcomingMovies = liveData(Dispatchers.IO) {
        val movies = upComingMoviesApi.getUpComingMovies().results
        emit(movies)
    }

    val nowPlayingMovies = liveData(Dispatchers.IO) {
        val movies = nowPlayingMoviesApi.getNowPlayingMovies().results
        emit(movies)
    }
}
