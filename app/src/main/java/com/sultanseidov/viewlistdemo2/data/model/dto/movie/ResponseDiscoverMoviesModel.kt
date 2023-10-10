package com.sultanseidov.viewlistdemo2.data.model.dto.movie

import com.google.gson.annotations.SerializedName
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import java.io.Serializable

data class ResponseDiscoverMoviesModel(
    @SerializedName("page")
    val page: Int = 1,
    @SerializedName("results")
    val movieModels: List<MovieModel>,
) : Serializable