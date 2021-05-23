package com.irfan.moviecatalogue.home


import androidx.lifecycle.*
import com.irfan.moviecatalogue.core.data.Resource
import com.irfan.moviecatalogue.core.domain.model.Movie
import com.irfan.moviecatalogue.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {
    private val _movie = MutableLiveData<Resource<List<Movie>>>()
    val movie: LiveData<Resource<List<Movie>>> = _movie

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            movieUseCase.getAllMovie().collect { result ->
                _movie.value = result
            }
        }
    }

}