package com.jicsoftwarestudio.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovie(
    @PrimaryKey val id: Int,
    val poster_path: String,
    val title: String,
    val vote_count: Int
)
