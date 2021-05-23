package com.irfan.moviecatalogue.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @field:SerializedName("id")
    var id: Int,

    @field:SerializedName("original_title")
    var title: String,

    @field:SerializedName("release_date")
    var releaseDate: String,

    @field:SerializedName("overview")
    var overview: String,

    @field:SerializedName("poster_path")
    var posterPath: String,

    @field:SerializedName("backdrop_path")
    var backdropPath: String,

    @field:SerializedName("vote_average")
    var score: Double,
)
