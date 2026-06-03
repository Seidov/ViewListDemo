package com.sultanseidov.viewlistdemo2.data.model.dto.movie

import kotlinx.serialization.Serializable

@Serializable
data class KeywordResponse(
    val id: Int,
    val keywords: List<KeywordDto>
)

@Serializable
data class KeywordDto(
    val id: Int,
    val name: String
)
