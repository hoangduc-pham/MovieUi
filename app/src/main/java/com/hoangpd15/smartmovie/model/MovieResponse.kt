package com.hoangpd15.smartmovie.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val results: List<Movie>
)
data class Movie(
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    val title: String,
    @SerializedName("vote_count")
    val voteCount: Int,
    val overview: String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
)