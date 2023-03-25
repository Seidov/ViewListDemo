package com.sultanseidov.viewlistdemo2.data.entity.movie

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseDiscoverMoviesModel(
    @SerializedName("page")
    val page: Int = 1,
    @SerializedName("results")
    val movieModels: List<MovieModel>,
) : Serializable