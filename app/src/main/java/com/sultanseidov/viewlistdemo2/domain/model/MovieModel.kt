package com.sultanseidov.viewlistdemo2.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sultanseidov.viewlistdemo2.util.Constants

@Entity(tableName = Constants.MOVIES_NEW_TABLE)
data class MovieModel(
    val id: Int,
    val adult: Boolean?,
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
) {
    @PrimaryKey(autoGenerate = true)
    var pk: Long = 0
}

fun MovieModel.toMovieViewList(): MoviesViewList {
    return MoviesViewList(
        id, adult, backdrop_path, genre_ids, original_language, original_title, overview, popularity, poster_path, release_date, title, video, vote_average, vote_count
    )
}