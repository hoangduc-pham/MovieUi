package com.example.domain.entities

data class CastMovie (
    val cast: List<Cast>
)

data class Cast (
    val name: String,
    val profilePath: String?
)