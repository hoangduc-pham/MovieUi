package com.hoangpd15.smartmovie.model

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    val overview: String,
    val title: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val runtime: Int,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<Language>,
    val genres: List<GenresMovie>
)

data class Language(
    @SerializedName("english_name")
    val englishName: String
)

data class GenresMovie(
    val name: String
)