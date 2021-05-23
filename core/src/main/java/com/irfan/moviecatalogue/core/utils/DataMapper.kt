package com.irfan.moviecatalogue.core.utils

import com.irfan.moviecatalogue.core.data.source.local.entity.MovieEntity
import com.irfan.moviecatalogue.core.data.source.remote.response.MovieResponse
import com.irfan.moviecatalogue.core.domain.model.Movie

object DataMapper {
    fun mapResponseToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                id = it.id,
                title = it.title,
                overview = it.overview,
                score = it.score,
                releaseDate = it.releaseDate,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                isFavorite = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                score = it.score,
                releaseDate = it.releaseDate,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Movie) = MovieEntity(
        id = input.id,
        title = input.title,
        overview = input.overview,
        score = input.score,
        releaseDate = input.releaseDate,
        posterPath = input.posterPath,
        backdropPath = input.backdropPath,
        isFavorite = input.isFavorite
    )

}