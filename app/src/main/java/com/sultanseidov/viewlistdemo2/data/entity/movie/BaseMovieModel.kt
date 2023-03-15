package com.sultanseidov.viewlistdemo2.data.entity.movie

import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.annotations.SerializedName
import java.io.Serializable

abstract class BaseMovieModel(
    @Transient
    @PrimaryKey(autoGenerate = true)
    open var pk: Long = 0,
    @Transient
    @SerializedName("id")
    open val movieId: Int,
    @Transient
    @SerializedName("overview")
    open val overview: String?,
    @Transient
    @SerializedName("poster_path")
    open val posterPath: String?,
    @Transient
    @SerializedName("title")
    open val title: String?,
    @Transient
    @SerializedName("vote_average")
    open val rating: String?,
    @Transient
    @SerializedName("genre_ids")
    open val genre_ids: List<Int>,
    @Transient
    @SerializedName("release_date")
    open val releaseDate: String?
) : Serializable


class TheTypeConverters {
    @TypeConverter
    fun fromListIntToString(intList: List<Int>): String = intList.toString()
    @TypeConverter
    fun toListIntFromString(stringList: String): List<Int> {
        val result = ArrayList<Int>()
        val split =stringList.replace("[","").replace("]","").replace(" ","").split(",")
        for (n in split) {
            try {
                result.add(n.toInt())
            } catch (e: Exception) {

            }
        }
        return result
    }
}