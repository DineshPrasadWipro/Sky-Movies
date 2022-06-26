package com.ds.skymovies.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("year") val year: String,
    @SerializedName("genre") val genre: String,
    @SerializedName("poster") val poster: String
)