package com.irfan.moviecatalogue.detail


import androidx.lifecycle.ViewModel
import com.irfan.moviecatalogue.core.domain.model.Movie
import com.irfan.moviecatalogue.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun setFavoriteMovie(movie: Movie, status: Boolean) =
        movieUseCase.setFavouriteMovie(movie, status)
}