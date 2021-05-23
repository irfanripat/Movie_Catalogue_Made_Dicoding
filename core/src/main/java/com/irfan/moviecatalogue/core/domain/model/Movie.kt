package com.irfan.moviecatalogue.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val score: Double,
    val releaseDate: String,
    val posterPath: String,
    val backdropPath: String,
    val isFavorite: Boolean
) : Parcelable
