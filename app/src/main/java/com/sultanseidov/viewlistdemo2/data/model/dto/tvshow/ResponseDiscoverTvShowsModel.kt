package com.sultanseidov.viewlistdemo2.data.model.dto.tvshow

import com.google.gson.annotations.SerializedName
import com.sultanseidov.viewlistdemo2.domain.model.TvShowModel
import java.io.Serializable

data class ResponseDiscoverTvShowsModel(
    @SerializedName("page")
    val page: Int = 1,
    @SerializedName("results")
    val tvShowModels: List<TvShowModel>
): Serializable