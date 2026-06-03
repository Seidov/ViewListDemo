package com.sultanseidov.viewlistdemo2.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watched_movies_table")
data class WatchedMovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val movieId: Long,
    val title: String,
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,
    @ColumnInfo(name = "watched_at")
    val watchedAt: Long,
    @ColumnInfo(name = "classification_version")
    val classificationVersion: Int = 2, // Yeni akıllı mimari versiyonu

    // Ham Yapay Zeka Girdileri
    val genres: List<Int>,
    val keywords: List<String>,

    // Gelişmiş Matematiksel Vektörler ve Profiller
    @ColumnInfo(name = "weighted_genre_profile")
    val weightedGenreProfile: Map<Int, Double>,
    @ColumnInfo(name = "genre_importance_score")
    val genreImportanceScore: Double,
    @ColumnInfo(name = "keyword_vector")
    val keywordVector: Map<String, Double>,
    @ColumnInfo(name = "semantic_identity")
    val semanticIdentity: List<String>,

    // Dinamik Kümeleme İmzaları (Transient Linkage)
    @ColumnInfo(name = "assigned_pin_id")
    val assignedPinId: Long?
)
