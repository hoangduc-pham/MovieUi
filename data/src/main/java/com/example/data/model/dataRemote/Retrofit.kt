package com.example.data.model.dataRemote

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    internal const val API_KEY = "d5b97a6fad46348136d87b78895a0c06"

    private val gson: Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()
    private fun createRetrofitService(serviceClass: Class<*>): Any {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(serviceClass)
    }
    val apiMoviePopular: MoviePopularApi by lazy {
        createRetrofitService(MoviePopularApi::class.java) as MoviePopularApi
    }
    val apiMovieTopRate: MovieTopRateApi by lazy {
        createRetrofitService(MovieTopRateApi::class.java) as MovieTopRateApi
    }

    val apiMovieUpComing: MovieUpComingApi by lazy {
        createRetrofitService(MovieUpComingApi::class.java) as MovieUpComingApi
    }

    val apiMovieNowPlaying: MovieNowPlayingApi by lazy {
        createRetrofitService(MovieNowPlayingApi::class.java) as MovieNowPlayingApi
    }

    val apiMovieSearch: MovieSearchApi by lazy {
        createRetrofitService(MovieSearchApi::class.java) as MovieSearchApi
    }

    val apiListGenres: GenresListApi by lazy {
        createRetrofitService(GenresListApi::class.java) as GenresListApi
    }

    val apiAllMovie: MovieAllApi by lazy {
        createRetrofitService(MovieAllApi::class.java) as MovieAllApi
    }

    val apiMovieDetail: MovieDetailApi by lazy {
        createRetrofitService(MovieDetailApi::class.java) as MovieDetailApi
    }

    val apiCastMovie: CastMovieApi by lazy {
        createRetrofitService(CastMovieApi::class.java) as CastMovieApi
    }
}