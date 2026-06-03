package com.sultanseidov.viewlistdemo2.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "discovery_remote_keys_new",
    primaryKeys = ["id", "categoryTag"]
)
data class DiscoveryRemoteKeysEntity(
    val id: Int,
    val categoryTag: String,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long = System.currentTimeMillis()
)
