package com.sultanseidov.viewlistdemo2.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseGenresListModel(
    @SerializedName("genres")
    val genres: List<GenreModel>
): Serializable