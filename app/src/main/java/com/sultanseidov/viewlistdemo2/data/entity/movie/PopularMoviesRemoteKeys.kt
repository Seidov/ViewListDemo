package com.sultanseidov.viewlistdemo2.data.entity.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sultanseidov.viewlistdemo2.util.Constants.POPULAR_MOVIES_REMOTE_KEYS_TABLE

@Entity(tableName = POPULAR_MOVIES_REMOTE_KEYS_TABLE)
data class PopularMoviesRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long?,
)