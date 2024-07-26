package com.hoangpd15.smartmovie.doumain

import com.hoangpd15.smartmovie.model.GenresResponse
import com.hoangpd15.smartmovie.model.MovieResponse
import com.hoangpd15.smartmovie.model.dataRemote.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository {
    suspend fun getPopularMovies(page: Int): MovieResponse {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiMoviePopular.getPopularMovies(page)
        }
    }

    suspend fun getTopRateMovies(page: Int): MovieResponse {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiMovieTopRate.getTopRateMovies(page)
        }
    }

    suspend fun getUpComingMovies(page: Int): MovieResponse {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiMovieUpComing.getUpComingMovies(page)
        }
    }

    suspend fun getNowPlayingMovies(page: Int): MovieResponse {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiMovieNowPlaying.getNowPlayingMovies(page)
        }
    }

    suspend fun getSearchMovies(query: String): MovieResponse {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiMovieSearch.searchMovies(query)
        }
    }

    suspend fun getMoviesByGenre(genreId: Int): MovieResponse {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiAllMovie.getMoviesByGenre(genreId)
        }
    }

    suspend fun getListGenres(): GenresResponse {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiListGenres.getListGenres()
        }
    }
}