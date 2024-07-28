package com.example.data.model

data class MovieResponse(
    val results: List<MovieDto>
)
data class MovieDto(
    val id: Int,
    val posterPath: String,
    val title: String,
    val voteCount: Int,
    val overview: String,
    val backdropPath: String,
    val voteAverage: Double,
)