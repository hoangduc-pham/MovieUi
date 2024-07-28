package com.example.domain.entities

data class GenresMovies(
    val genres: List<Genres>
)
data class Genres(
    val id: Int,
    val name: String,
)