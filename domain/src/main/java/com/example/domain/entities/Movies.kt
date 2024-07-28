package com.example.domain.entities

data class Movies(
    val results: List<Movie>
)
data class Movie(
    val id: Int,
    val posterPath: String,
    val title: String,
    val voteCount: Int,
    val overview: String,
    val backdropPath: String,
    val voteAverage: Double,
)