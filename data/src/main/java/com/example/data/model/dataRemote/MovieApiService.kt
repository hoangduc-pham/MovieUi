package com.example.data.model.dataRemote

import com.example.domain.entities.CastMovie
import com.example.domain.entities.GenresMovie
import com.example.domain.entities.GenresMovies
import com.example.domain.entities.MovieDetail
import com.example.domain.entities.Movies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviePopularApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = RetrofitInstance.API_KEY
    ): Movies
}

interface MovieTopRateApi {
    @GET("movie/top_rated")
    suspend fun getTopRateMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = RetrofitInstance.API_KEY
    ): Movies
}

interface MovieUpComingApi {
    @GET("movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = RetrofitInstance.API_KEY
    ): Movies
}

interface MovieNowPlayingApi {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = RetrofitInstance.API_KEY
    ): Movies
}

interface MovieSearchApi {
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") textSearch: String,
        @Query("api_key") apiKey: String = RetrofitInstance.API_KEY
    ): Movies
}

interface GenresListApi {
    @GET("genre/movie/list")
    suspend fun getListGenres(@Query("api_key") apiKey: String = RetrofitInstance.API_KEY): GenresMovies
}

interface MovieAllApi {
    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("with_genres") genreId: Int,
        @Query("api_key") apiKey: String = RetrofitInstance.API_KEY
    ): Movies
}

interface MovieDetailApi {
    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") idMovie: Int,
        @Query("api_key") apiKey: String = RetrofitInstance.API_KEY
    ): MovieDetail
}

interface CastMovieApi {
    @GET("movie/{id}/credits")
    suspend fun getCastMovie(
        @Path("id") idMovie: Int,
        @Query("api_key") apiKey: String = RetrofitInstance.API_KEY
    ): CastMovie
}
