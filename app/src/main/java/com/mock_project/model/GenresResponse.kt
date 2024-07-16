package com.mock_project.model

data class GenresResponse(
    val genres: List<Genres>
)
data class Genres(
    val id: Int,
    val name: String,
)