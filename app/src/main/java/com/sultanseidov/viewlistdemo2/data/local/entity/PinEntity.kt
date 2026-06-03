package com.sultanseidov.viewlistdemo2.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pins_table")
data class PinEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pin_id")
    val pinId: Long = 0,
    val title: String,
    @ColumnInfo(name = "semantic_type")
    val semanticType: String,
    @ColumnInfo(name = "dominant_genres")
    val dominantGenres: List<Int>,
    @ColumnInfo(name = "movie_count")
    val movieCount: Int,
    @ColumnInfo(name = "cluster_strength")
    val clusterStrength: Double,
    @ColumnInfo(name = "generated_from_movies")
    val generatedFromMovies: List<Long>, // Bu pini var eden kaynak film ID listesi (Geriye dönük re-cluster için)
    @ColumnInfo(name = "last_recomputed_at")
    val lastRecomputedAt: Long
)
