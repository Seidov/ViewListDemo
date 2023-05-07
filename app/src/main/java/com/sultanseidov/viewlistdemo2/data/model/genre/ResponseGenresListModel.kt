package com.sultanseidov.viewlistdemo2.data.model.genre

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseGenresListModel(
    @SerializedName("genres")
    val genres: List<GenresMovieModel>
): Serializable