package com.example.domain.entities

data class MovieDetail(
    val id: Int,
    val posterPath: String,
    val overview: String,
    val title: String,
    val releaseDate: String,
    val runtime: Int,
    val voteAverage: Double,
    val spokenLanguages: List<Language>,
    val genres: List<GenresMovie>
)

data class Language(
    val englishName: String
)

data class GenresMovie(
    val name: String
)