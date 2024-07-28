package com.example.domain

import com.example.domain.entities.CastMovie
import com.example.domain.entities.FavoriteMovieEntity
import com.example.domain.entities.GenresMovies
import com.example.domain.entities.MovieDetail
import com.example.domain.entities.Movies

interface IMovieRepository {
    suspend fun getPopularMovies(page: Int): Movies
    suspend fun getTopRateMovies(page: Int): Movies
    suspend fun getUpComingMovies(page: Int): Movies
    suspend fun getNowPlayingMovies(page: Int): Movies
    suspend fun getSearchMovies(query: String): Movies
    suspend fun getMoviesByGenre(genreId: Int): Movies
    suspend fun getListGenres(): GenresMovies
    suspend fun getMovieDetails(movieId: Int): MovieDetail
    suspend fun getCastMovie(movieId: Int): CastMovie
    suspend fun getInsert(movie: FavoriteMovieEntity)
    suspend fun getDelete(movieId: Int)
    suspend fun getAllFavoriteMovies(): List<FavoriteMovieEntity>
    suspend fun isFavoriteMovie(movieId: Int): Boolean
}