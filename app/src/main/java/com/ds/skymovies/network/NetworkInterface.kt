package com.ds.skymovies.network

import com.ds.skymovies.model.Movies
import retrofit2.Response
import retrofit2.http.GET

interface NetworkInterface {

    @GET("movies")
    suspend fun getMovies(): Response<Movies>


}