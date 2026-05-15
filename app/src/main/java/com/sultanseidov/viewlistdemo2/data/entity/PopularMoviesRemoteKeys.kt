package com.sultanseidov.viewlistdemo2.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sultanseidov.viewlistdemo2.util.Constants.POPULAR_MOVIES_REMOTE_KEYS_TABLE
import kotlinx.serialization.Serializable

@Entity(tableName = POPULAR_MOVIES_REMOTE_KEYS_TABLE)
data class PopularMoviesRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long?,
)