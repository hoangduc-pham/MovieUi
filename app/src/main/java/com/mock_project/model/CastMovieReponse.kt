package com.mock_project.model

import com.google.gson.annotations.SerializedName

data class CastMovieReponse (
    val cast: List<Cast>
)

data class Cast (
    val name: String,

    @SerializedName("profile_path")
    val profilePath: String
)