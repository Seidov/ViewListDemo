package com.sultanseidov.viewlistdemo2.data.entity.tvshow

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sultanseidov.viewlistdemo2.util.Constants.TV_SHOW_REMOTE_KEYS_TABLE

@Entity(tableName = TV_SHOW_REMOTE_KEYS_TABLE)
data class TvShowsRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long?,
)