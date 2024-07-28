package com.example.data.model

data class GenresResponse(
    val genres: List<GenreDto>
)
data class GenreDto(
    val id: Int,
    val name: String,
)