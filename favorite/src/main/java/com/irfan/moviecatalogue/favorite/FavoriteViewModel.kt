package com.irfan.moviecatalogue.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.irfan.moviecatalogue.core.domain.usecase.MovieUseCase


class FavoriteViewModel (movieUseCase: MovieUseCase) : ViewModel() {

    val favoriteMovie = movieUseCase.getFavoriteMovie().asLiveData()

}