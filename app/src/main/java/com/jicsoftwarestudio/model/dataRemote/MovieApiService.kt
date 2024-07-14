package com.jicsoftwarestudio.model.dataRemote

import com.jicsoftwarestudio.model.MovieResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviePopularApi {
    @GET("movie/popular?api_key=d5b97a6fad46348136d87b78895a0c06&page=5")
    suspend fun getPopularMovies(): MovieResponse
}
interface MovieTopRateApi {
    @GET("movie/top_rated?api_key=d5b97a6fad46348136d87b78895a0c06&page=2")
    suspend fun getTopRateMovies(): MovieResponse
}
interface MovieUpComingApi {
    @GET("movie/upcoming?api_key=d5b97a6fad46348136d87b78895a0c06&page=3")
    suspend fun getUpComingMovies(): MovieResponse
}
interface MovieNowPlayingApi {
    @GET("movie/now_playing?api_key=d5b97a6fad46348136d87b78895a0c06&page=4")
    suspend fun getNowPlayingMovies(): MovieResponse
}
interface MovieSearchApi {
    @GET("https://api.themoviedb.org/3/search/movie?api_key=d5b97a6fad46348136d87b78895a0c06")
    suspend fun searchMovies(@Query("query") textSearch: String): MovieResponse
}

object RetrofitInstance {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    val apiMoviePopular: MoviePopularApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviePopularApi::class.java)
    }

    val apiMovieTopRate: MovieTopRateApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieTopRateApi::class.java)
    }

    val apiMovieUpComing: MovieUpComingApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieUpComingApi::class.java)
    }

    val apiMovieNowPlaying: MovieNowPlayingApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieNowPlayingApi::class.java)
    }
    val apiMovieSearch: MovieSearchApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieSearchApi::class.java)
    }
}