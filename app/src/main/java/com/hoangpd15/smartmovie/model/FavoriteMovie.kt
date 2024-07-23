package com.hoangpd15.smartmovie.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovie(
    @PrimaryKey val id: Int,
    val posterPath: String,
    val title: String,
    val voteCount: Int,
    val overView: String
)
