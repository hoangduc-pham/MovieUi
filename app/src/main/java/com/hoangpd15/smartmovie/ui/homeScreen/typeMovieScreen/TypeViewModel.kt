package com.hoangpd15.smartmovie.ui.homeScreen.typeMovieScreen

import com.hoangpd15.smartmovie.model.Movie
import com.hoangpd15.smartmovie.model.dataRemote.RetrofitInstance
import com.hoangpd15.smartmovie.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NowPlayingViewModel : BaseViewModel() {
    override suspend fun fetchMoviesFromApi(page: Int): List<Movie> {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiMovieNowPlaying.getNowPlayingMovies(page).results
        }
    }
}

class PopularViewModel : BaseViewModel() {
    override suspend fun fetchMoviesFromApi(page: Int): List<Movie> {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiMoviePopular.getPopularMovies(page).results
        }
    }
}

class TopRatedViewModel : BaseViewModel() {
    override suspend fun fetchMoviesFromApi(page: Int): List<Movie> {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiMovieTopRate.getTopRateMovies(page).results
        }
    }
}

class UpComingViewModel : BaseViewModel() {
    override suspend fun fetchMoviesFromApi(page: Int): List<Movie> {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiMovieUpComing.getUpComingMovies(page).results
        }
    }
}
