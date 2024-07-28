package com.example.data.model

import com.example.domain.entities.Cast
import com.example.domain.entities.CastMovie
import com.example.domain.entities.FavoriteMovieEntity

fun FavoriteMovieEntity.toDomain(): FavoriteMovie {
    return FavoriteMovie(
        id = id,
        posterPath = posterPath,
        title = title,
        voteCount = voteCount,
        overView = overView
    )
}

fun FavoriteMovie.toEntity(): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        id = id,
        posterPath = posterPath,
        title = title,
        voteCount = voteCount,
        overView = overView
    )
}


fun CastMovieResponse.toCastMovie(): CastMovie {
    return CastMovie(
        cast = this.cast.map { it.toCast() }
    )
}

fun CastDto.toCast(): Cast {
    return Cast(
        name = this.name,
        profilePath = this.profilePath
    )
}