package com.sultanseidov.viewlistdemo2.data.model.dto.genre

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseMovieGenresListModel(
    @SerializedName("genres")
    val genres: List<GenresMovieModel>
): Serializable