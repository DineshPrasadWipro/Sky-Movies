package com.ds.skymovies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ds.skymovies.model.CustomMovie
import com.ds.skymovies.model.Movies
import com.ds.skymovies.repository.INetworkRepository
import com.ds.skymovies.util.NetworkResponse
import kotlinx.coroutines.launch

class MovieListViewModel(private val networkRepository: INetworkRepository) : ViewModel() {

    private val _movieList = MutableLiveData<List<CustomMovie>>()
    val movieList: LiveData<List<CustomMovie>> = _movieList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String?> = _errorMessage

    val loading = MutableLiveData<Boolean>()

    private lateinit var customMovies: MutableList<CustomMovie>

    val searchText = MutableLiveData<String?>(null)

    init {
        getMovies()
    }

    fun getMovies() {
        loading.value = true
        viewModelScope.launch {

            when (val response = networkRepository.getMovies()) {
                is NetworkResponse.Success -> setValues(response.data)
                is NetworkResponse.Error -> _errorMessage.value = response.message
                is NetworkResponse.NetworkException -> _errorMessage.value =
                    response.exception.message
            }

            loading.value = false
        }
    }

    private fun setValues(movies: Movies) {
        customMovies = mutableListOf()
        movies.data.forEach() { movie ->
            val customMovie =
                CustomMovie(movie.id, movie.title, movie.year, movie.genre, movie.poster)
            customMovies.add(customMovie)
        }
        _movieList.value = customMovies
    }

    fun onQueryChanged(query: String): Boolean {
        searchText.postValue(query)
        return true
    }

}