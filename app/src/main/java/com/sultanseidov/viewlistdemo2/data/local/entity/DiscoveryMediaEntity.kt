package com.sultanseidov.viewlistdemo2.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "discovery_cache_table",
    primaryKeys = ["id", "categoryTag"]
)
data class DiscoveryMediaEntity(
    val id: Int,
    val categoryTag: String, // "MOVIES", "TV_SHOWS", or "PIN_{id}"
    val title: String?,
    val posterPath: String?,
    val mediaType: String, // "MOVIE" or "TV"
    val page: Int,
    val indexInPage: Int,
    val voteAverage: Double? = 0.0
)
