package com.ds.skymovies.util

sealed class NetworkResponse<T>(
    data: T? = null,
    message: String? = null,
    exception: Exception? = null
) {
    data class Success<T>(val data: T) : NetworkResponse<T>(data, null)

    data class Error<T>(
        val message: String
    ) : NetworkResponse<T>(null, message)

    data class NetworkException<T>(
        val exception: Exception
    ) : NetworkResponse<T>(null, message = null, exception)

}