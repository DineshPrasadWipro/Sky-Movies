package com.ds.skymovies.repository

import com.ds.skymovies.model.Movies
import com.ds.skymovies.network.NetworkInterface
import com.ds.skymovies.util.NetworkResponse

class NetworkRepository(private val networkInterface: NetworkInterface) : INetworkRepository {
    override suspend fun getMovies(): NetworkResponse<Movies> {
        return try {
            val response = networkInterface.getMovies()
            if (response.isSuccessful) {
                NetworkResponse.Success(data = response.body() as Movies)
            } else {
                NetworkResponse.Error(message = response.message().toString())
            }
        } catch (exception: Exception) {
            NetworkResponse.NetworkException(exception = exception)
        }
    }

}
