package com.jicsoftwarestudio.model

data class MovieResponse(
    val results: List<Movie>
)
data class Movie(
    val id: Int,
    val poster_path: String,
    val title: String,
    val vote_count: Int,
    val backdrop_path: String,
    val vote_average: Double
)