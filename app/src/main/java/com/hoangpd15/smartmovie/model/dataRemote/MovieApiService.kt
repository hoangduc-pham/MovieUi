package com.hoangpd15.smartmovie.model.dataRemote

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hoangpd15.smartmovie.model.CastMovieReponse
import com.hoangpd15.smartmovie.model.GenresResponse
import com.hoangpd15.smartmovie.model.MovieDetailResponse
import com.hoangpd15.smartmovie.model.MovieResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviePopularApi {
    @GET("movie/popular?api_key=d5b97a6fad46348136d87b78895a0c06&page")
    suspend fun getPopularMovies(@Query("page") page: Int): MovieResponse
}
interface MovieTopRateApi {
    @GET("movie/top_rated?api_key=d5b97a6fad46348136d87b78895a0c06&page")
    suspend fun getTopRateMovies(@Query("page") page: Int): MovieResponse
}
interface MovieUpComingApi {
    @GET("movie/upcoming?api_key=d5b97a6fad46348136d87b78895a0c06&page")
    suspend fun getUpComingMovies(@Query("page") page: Int): MovieResponse
}
interface MovieNowPlayingApi {
    @GET("movie/now_playing?api_key=d5b97a6fad46348136d87b78895a0c06&page")
    suspend fun getNowPlayingMovies(@Query("page") page: Int): MovieResponse
}
interface MovieSearchApi {
    @GET("https://api.themoviedb.org/3/search/movie?api_key=d5b97a6fad46348136d87b78895a0c06")
    suspend fun searchMovies(@Query("query") textSearch: String): MovieResponse
}
interface GenresListApi {
    @GET("genre/movie/list?api_key=d5b97a6fad46348136d87b78895a0c06&page=2")
    suspend fun getListGenres(): GenresResponse
}
interface MovieAllApi {
    @GET("discover/movie?include_adult=true&include_video=true&language=en-US&page=1&sort_by=popularity.desc&api_key=d5b97a6fad46348136d87b78895a0c06")
    suspend fun getMoviesByGenre(@Query("with_genres") genreId: Int): MovieResponse
}
interface MovieDetailApi {
    @GET("movie/{id}?api_key=d5b97a6fad46348136d87b78895a0c06")
    suspend fun getMovieDetails(@Path("id") idMovie: Int): MovieDetailResponse
}
interface CastMovieApi {
    @GET("movie/{id}/credits?api_key=d5b97a6fad46348136d87b78895a0c06")
    suspend fun getCastMovie(@Path("id") idMovie: Int): CastMovieReponse
}

object RetrofitInstance {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    private val gson: Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    val apiMoviePopular: MoviePopularApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MoviePopularApi::class.java)
    }

    val apiMovieTopRate: MovieTopRateApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MovieTopRateApi::class.java)
    }

    val apiMovieUpComing: MovieUpComingApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MovieUpComingApi::class.java)
    }

    val apiMovieNowPlaying: MovieNowPlayingApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MovieNowPlayingApi::class.java)
    }
    val apiMovieSearch: MovieSearchApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MovieSearchApi::class.java)
    }
    val apiListGenres: GenresListApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(GenresListApi::class.java)
    }
    val apiAllMovie: MovieAllApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MovieAllApi::class.java)
    }
    val apiMovieDetail: MovieDetailApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MovieDetailApi::class.java)
    }
    val apiCastMovie: CastMovieApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CastMovieApi::class.java)
    }
}