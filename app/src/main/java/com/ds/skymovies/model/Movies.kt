package com.ds.skymovies.model

import com.google.gson.annotations.SerializedName

data class Movies(@SerializedName("data") val data: List<Movie>)
