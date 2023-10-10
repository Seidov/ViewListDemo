package com.sultanseidov.viewlistdemo2.data.model.dto.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sultanseidov.viewlistdemo2.util.Constants.MOVIES_NEW_REMOTE_KEYS_TABLE

@Entity(tableName = MOVIES_NEW_REMOTE_KEYS_TABLE)
data class MoviesRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long?,
)