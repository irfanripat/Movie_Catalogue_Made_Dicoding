package com.irfan.moviecatalogue.core.domain.repository

import com.irfan.moviecatalogue.core.data.Resource
import com.irfan.moviecatalogue.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun getAllMovie() : Flow<Resource<List<Movie>>>

    fun getFavoriteMovie() : Flow<List<Movie>>

    fun setFavouriteMovie(movie: Movie, isFavourite: Boolean)
}