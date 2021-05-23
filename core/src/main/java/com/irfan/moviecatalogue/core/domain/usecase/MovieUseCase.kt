package com.irfan.moviecatalogue.core.domain.usecase

import com.irfan.moviecatalogue.core.data.Resource
import com.irfan.moviecatalogue.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    fun getAllMovie() : Flow<Resource<List<Movie>>>

    fun getFavoriteMovie() : Flow<List<Movie>>

    fun setFavouriteMovie(movie: Movie, isFavourite: Boolean)
}