package com.sultanseidov.viewlistdemo2.data.model.viewlist

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sultanseidov.viewlistdemo2.data.model.dto.tvshow.BaseTvShowModel

data class ViewListTvShowsModel(
    @PrimaryKey(autoGenerate = true)
    override var pk: Long = 0,
    @SerializedName("backdrop_path")
    override val backdrop_path: String?="",
    @SerializedName("first_air_date")
    override val first_air_date: String,
    @SerializedName("genre_ids")
    override val genre_ids: List<Int>,
    @SerializedName("id")
    override val tvShowId: Int,
    @SerializedName("name")
    override val name: String,
    @SerializedName("original_language")
    override val original_language: String,
    @SerializedName("original_name")
    override val original_name: String,
    @SerializedName("overview")
    override val overview: String,
    @SerializedName("popularity")
    override val popularity: Double,
    @SerializedName("poster_path")
    override val poster_path: String?="",
    @SerializedName("vote_average")
    override val vote_average: Double,
    @SerializedName("vote_count")
    override val vote_count: Int
) : BaseTvShowModel(pk,backdrop_path,first_air_date,genre_ids,tvShowId,name,original_language,original_name, overview, popularity, poster_path, vote_average, vote_count)