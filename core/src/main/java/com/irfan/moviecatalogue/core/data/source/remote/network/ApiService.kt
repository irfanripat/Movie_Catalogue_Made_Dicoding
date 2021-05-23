package com.irfan.moviecatalogue.core.data.source.remote.network

import com.irfan.moviecatalogue.core.BuildConfig
import com.irfan.moviecatalogue.core.data.source.remote.response.ListMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("api_key") apiKey : String = BuildConfig.API_KEY
    ) : ListMovieResponse

}