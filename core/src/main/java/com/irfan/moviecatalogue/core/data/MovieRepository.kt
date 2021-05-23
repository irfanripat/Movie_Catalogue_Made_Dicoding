package com.irfan.moviecatalogue.core.data

import com.irfan.moviecatalogue.core.data.source.local.LocalDataSource
import com.irfan.moviecatalogue.core.data.source.remote.RemoteDataSource
import com.irfan.moviecatalogue.core.data.source.remote.network.ApiResponse
import com.irfan.moviecatalogue.core.data.source.remote.response.MovieResponse
import com.irfan.moviecatalogue.core.domain.model.Movie
import com.irfan.moviecatalogue.core.domain.repository.IMovieRepository
import com.irfan.moviecatalogue.core.utils.AppExecutors
import com.irfan.moviecatalogue.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    override fun getAllMovie(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovie().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovie()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapResponseToEntities(data)
                localDataSource.insertMovie(movieList)
            }
        }.asFlow()

    override fun getFavoriteMovie(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovie().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavouriteMovie(movie: Movie, isFavourite: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movieEntity, isFavourite)}
    }
}