package com.ds.skymovies.repository

import com.ds.skymovies.model.Movie
import com.ds.skymovies.model.Movies
import com.ds.skymovies.util.NetworkResponse
import java.net.SocketTimeoutException

class MockNetworkRepository : INetworkRepository {

    private val movieList: MutableList<Movie> = mutableListOf()
    var movies = Movies(movieList)
    private var response: Int = SUCCESS

    override suspend fun getMovies(): NetworkResponse<Movies> {

        when (response) {
            SUCCESS -> return NetworkResponse.Success(movies)
            ERROR -> return NetworkResponse.Error("404")
            EXCEPTION -> return NetworkResponse.NetworkException(SocketTimeoutException("Network Exception"))
        }
        return NetworkResponse.Success(movies)
    }

    fun setMovies() {

        for (i in 0..2) {
            val movie = Movie(i.toString(), "Animal", "12", "ss", "https://")
            movieList.add(movie)
        }
        movies = Movies(movieList)

    }

    fun setException() {
        response = EXCEPTION

    }

    fun setError() {
        response = ERROR

    }

    companion object {
        var SUCCESS = 1
        var ERROR = 2
        var EXCEPTION = 3
    }
}