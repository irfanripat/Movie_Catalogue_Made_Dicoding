package com.irfan.moviecatalogue.core.domain.usecase

import com.irfan.moviecatalogue.core.data.Resource
import com.irfan.moviecatalogue.core.domain.model.Movie
import com.irfan.moviecatalogue.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository) : MovieUseCase {

    override fun getAllMovie(): Flow<Resource<List<Movie>>> = movieRepository.getAllMovie()

    override fun getFavoriteMovie(): Flow<List<Movie>> = movieRepository.getFavoriteMovie()

    override fun setFavouriteMovie(movie: Movie, isFavourite: Boolean) = movieRepository.setFavouriteMovie(movie, isFavourite)

}