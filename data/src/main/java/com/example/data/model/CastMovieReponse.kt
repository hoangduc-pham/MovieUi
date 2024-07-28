package com.example.data.model

data class CastMovieResponse (
    val cast: List<CastDto>
)

data class CastDto (
    val name: String,
    val profilePath: String?
)