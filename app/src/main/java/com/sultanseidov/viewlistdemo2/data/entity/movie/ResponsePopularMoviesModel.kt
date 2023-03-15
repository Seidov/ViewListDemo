package com.sultanseidov.viewlistdemo2.data.entity.movie

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponsePopularMoviesModel(
    @SerializedName("page")
    val page: Int = 1,
    @SerializedName("results")
    val movies: List<PopularMoviesModel>,
) : Serializable