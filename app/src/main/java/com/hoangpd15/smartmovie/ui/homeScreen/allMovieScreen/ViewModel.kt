package com.hoangpd15.smartmovie.ui.homeScreen.allMovieScreen

import com.hoangpd15.smartmovie.model.Movie
import com.hoangpd15.smartmovie.model.dataRemote.RetrofitInstance
import com.hoangpd15.smartmovie.base.BaseViewModel

class NowPlayingViewModel : BaseViewModel() {
    override suspend fun fetchMoviesFromApi(page: Int): List<Movie> {
        return RetrofitInstance.apiMovieNowPlaying.getNowPlayingMovies(page).results
    }
}

class PopularViewModel : BaseViewModel() {
    override suspend fun fetchMoviesFromApi(page: Int): List<Movie> {
        return RetrofitInstance.apiMoviePopular.getPopularMovies(page).results
    }
}

class TopRatedViewModel : BaseViewModel() {
    override suspend fun fetchMoviesFromApi(page: Int): List<Movie> {
        return RetrofitInstance.apiMovieTopRate.getTopRateMovies(page).results
    }
}

class UpComingViewModel : BaseViewModel() {
    override suspend fun fetchMoviesFromApi(page: Int): List<Movie> {
        return RetrofitInstance.apiMovieUpComing.getUpComingMovies(page).results
    }
}
