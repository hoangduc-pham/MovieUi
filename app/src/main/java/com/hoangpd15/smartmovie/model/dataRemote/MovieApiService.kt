package com.hoangpd15.smartmovie.model.dataRemote

import com.hoangpd15.smartmovie.model.CastMovieReponse
import com.hoangpd15.smartmovie.model.GenresResponse
import com.hoangpd15.smartmovie.model.MovieDetailResponse
import com.hoangpd15.smartmovie.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviePopularApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int, @Query("api_key") apiKey: String = RetrofitInstance.API_KEY): MovieResponse
}

interface MovieTopRateApi {
    @GET("movie/top_rated")
    suspend fun getTopRateMovies(@Query("page") page: Int, @Query("api_key") apiKey: String = RetrofitInstance.API_KEY): MovieResponse
}

interface MovieUpComingApi {
    @GET("movie/upcoming")
    suspend fun getUpComingMovies(@Query("page") page: Int, @Query("api_key") apiKey: String = RetrofitInstance.API_KEY): MovieResponse
}

interface MovieNowPlayingApi {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page: Int, @Query("api_key") apiKey: String = RetrofitInstance.API_KEY): MovieResponse
}

interface MovieSearchApi {
    @GET("search/movie")
    suspend fun searchMovies(@Query("query") textSearch: String, @Query("api_key") apiKey: String = RetrofitInstance.API_KEY): MovieResponse
}

interface GenresListApi {
    @GET("genre/movie/list")
    suspend fun getListGenres(@Query("api_key") apiKey: String = RetrofitInstance.API_KEY): GenresResponse
}

interface MovieAllApi {
    @GET("discover/movie")
    suspend fun getMoviesByGenre(@Query("with_genres") genreId: Int, @Query("api_key") apiKey: String = RetrofitInstance.API_KEY): MovieResponse
}

interface MovieDetailApi {
    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") idMovie: Int, @Query("api_key") apiKey: String = RetrofitInstance.API_KEY): MovieDetailResponse
}

interface CastMovieApi {
    @GET("movie/{id}/credits")
    suspend fun getCastMovie(@Path("id") idMovie: Int, @Query("api_key") apiKey: String = RetrofitInstance.API_KEY): CastMovieReponse
}
