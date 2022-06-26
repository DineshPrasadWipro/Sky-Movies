package com.ds.skymovies.repository

import com.ds.skymovies.model.Movies
import com.ds.skymovies.util.NetworkResponse

interface INetworkRepository {
    suspend fun getMovies(): NetworkResponse<Movies>
}