package com.sultanseidov.viewlistdemo2.data.model.dto.movie

import com.sultanseidov.viewlistdemo2.domain.model.MovieModel

data class MoviesDto(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)