package com.mock_project.model.dataRemote

import com.mock_project.model.CastMovieReponse
import com.mock_project.model.GenresResponse
import com.mock_project.model.MovieDetailResponse
import com.mock_project.model.MovieResponse
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
    val apiListGenres: GenresListApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GenresListApi::class.java)
    }
    val apiAllMovie: MovieAllApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAllApi::class.java)
    }
    val apiMovieDetail: MovieDetailApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieDetailApi::class.java)
    }
    val apiCastMovie: CastMovieApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CastMovieApi::class.java)
    }
}