package com.sultanseidov.viewlistdemo2.data.model.viewlist

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sultanseidov.viewlistdemo2.data.model.dto.movie.BaseMovieModel


data class ViewListMoviesModel2(
    @PrimaryKey(autoGenerate = true)
    override var pk: Long = 0,
    @SerializedName("id")
    override val movieId: Int,
    @SerializedName("overview")
    override val overview: String?,
    @SerializedName("poster_path")
    override val posterPath: String?,
    @SerializedName("title")
    override val title: String?,
    @SerializedName("vote_average")
    override val rating: String?,
    @SerializedName("genre_ids")
    override val genre_ids: List<Int>,
    @SerializedName("release_date")
    override val releaseDate: String?
): BaseMovieModel(pk, movieId, overview, posterPath, title, rating, genre_ids, releaseDate)


