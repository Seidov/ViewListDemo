package com.sultanseidov.viewlistdemo2.domain.model

data class MovieBehaviorProfile(
    val movieId: Long,
    val title: String,
    val posterPath: String?,
    val genres: List<Int>,
    val keywords: List<String>,
    val weightedGenreProfile: Map<String, Double>,
    val keywordVector: Map<String, Double>,
    val semanticIdentity: List<String>,
    val recencyScore: Double
)
