package com.sultanseidov.viewlistdemo2.data.entity.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sultanseidov.viewlistdemo2.util.Constants.MOVIES_TABLE


@Entity(tableName = MOVIES_TABLE)
data class MovieModel(
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


