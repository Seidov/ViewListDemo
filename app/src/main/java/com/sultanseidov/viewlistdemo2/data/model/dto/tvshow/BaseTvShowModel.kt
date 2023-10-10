package com.sultanseidov.viewlistdemo2.data.model.dto.tvshow

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

abstract class BaseTvShowModel(

    @Transient
    @PrimaryKey(autoGenerate = true)
    open var pk: Long = 0,

    @Transient
    @SerializedName("backdrop_path")
    open val backdrop_path: String?="",

    @Transient
    @SerializedName("first_air_date")
    open val first_air_date: String,

    @Transient
    @SerializedName("genre_ids")
    open val genre_ids: List<Int>,

    @Transient
    @SerializedName("id")
    open val tvShowId: Int,

    @Transient
    @SerializedName("name")
    open val name: String,

    //@Transient
    //@SerializedName("origin_country")
    //val origin_country: List<String>,

    @Transient
    @SerializedName("original_language")
    open val original_language: String,

    @Transient
    @SerializedName("original_name")
    open val original_name: String,

    @Transient
    @SerializedName("overview")
    open val overview: String,

    @Transient
    @SerializedName("popularity")
    open val popularity: Double,

    @Transient
    @SerializedName("poster_path")
    open val poster_path: String?="",

    @Transient
    @SerializedName("vote_average")
    open val vote_average: Double,

    @Transient
    @SerializedName("vote_count")
    open val vote_count: Int

): Serializable
