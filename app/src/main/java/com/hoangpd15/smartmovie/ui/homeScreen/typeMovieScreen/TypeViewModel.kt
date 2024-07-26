package com.hoangpd15.smartmovie.ui.homeScreen.typeMovieScreen

import com.hoangpd15.smartmovie.model.Movie
import com.hoangpd15.smartmovie.model.dataRemote.RetrofitInstance
import com.hoangpd15.smartmovie.base.BaseViewModel
import com.hoangpd15.smartmovie.doumain.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(private val repository: MovieRepository) : BaseViewModel() {
    override suspend fun fetchMoviesFromApi(page: Int): List<Movie> {
        return repository.getNowPlayingMovies(page).results
    }
}

@HiltViewModel
class PopularViewModel @Inject constructor(private val repository: MovieRepository) : BaseViewModel() {
    override suspend fun fetchMoviesFromApi(page: Int): List<Movie> {
        return repository.getPopularMovies(page).results
    }
}

@HiltViewModel
class TopRatedViewModel @Inject constructor(private val repository: MovieRepository) : BaseViewModel() {
    override suspend fun fetchMoviesFromApi(page: Int): List<Movie> {
        return repository.getTopRateMovies(page).results
    }
}

@HiltViewModel
class UpComingViewModel @Inject constructor(private val repository: MovieRepository) : BaseViewModel() {
    override suspend fun fetchMoviesFromApi(page: Int): List<Movie> {
        return repository.getUpComingMovies(page).results
    }
}
