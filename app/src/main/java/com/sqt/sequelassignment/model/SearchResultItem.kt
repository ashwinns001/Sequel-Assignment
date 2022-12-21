package com.sqt.sequelassignment.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchResultItem(
    val Title: String,
    val Year: String,
    @PrimaryKey
    val imdbID: String,
    val Type: String, val Poster: String,
)