package com.hoangpd15.smartmovie.model

import com.google.gson.annotations.SerializedName

data class CastMovieReponse (
    val cast: List<Cast>
)

data class Cast (
    val name: String,
    val profilePath: String?
)