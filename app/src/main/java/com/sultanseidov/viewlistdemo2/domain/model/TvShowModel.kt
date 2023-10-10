package com.sultanseidov.viewlistdemo2.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sultanseidov.viewlistdemo2.util.Constants

@Entity(tableName = Constants.TV_SHOW_NEW_TABLE)
data class TvShowModel(
    val backdrop_path: String?,
    val first_air_date: String?,
    val genre_ids: List<Int>?,
    val id: Int,
    val name: String?,
    val original_language: String?,
    val original_name: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val vote_average: Double?,
    val vote_count: Int?
) {
    @PrimaryKey(autoGenerate = true)
    var pk: Long = 0
}

fun TvShowModel.toTVShowViewList(): TVShowsViewList {
    return TVShowsViewList(
        backdrop_path,
        first_air_date,
        genre_ids,
        id,
        name,
        original_language,
        original_name,
        overview,
        popularity,
        poster_path,
        vote_average,
        vote_count
    )
}