package com.irfan.moviecatalogue.core.data.source.local

import com.irfan.moviecatalogue.core.data.source.local.entity.MovieEntity
import com.irfan.moviecatalogue.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val movieDao: MovieDao) {

    fun getAllMovie() : Flow<List<MovieEntity>> = movieDao.getAllMovie()

    fun getFavoriteMovie() : Flow<List<MovieEntity>> = movieDao.getFavoriteMovie()

    suspend fun insertMovie(movieList: List<MovieEntity>) = movieDao.insertMovie(movieList)

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        movieDao.updateFavoriteMovie(movie)
    }
}