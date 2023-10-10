package com.sultanseidov.viewlistdemo2.data.model.dto.genre

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseTVShowGenresListModel(
    @SerializedName("genres")
    val genres: List<GenresTvShowModel>
): Serializable