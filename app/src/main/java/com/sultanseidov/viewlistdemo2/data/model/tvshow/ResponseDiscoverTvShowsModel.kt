package com.sultanseidov.viewlistdemo2.data.model.tvshow

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseDiscoverTvShowsModel(
    @SerializedName("page")
    val page: Int = 1,
    @SerializedName("results")
    val tvShowModels: List<TvShowModel>
): Serializable