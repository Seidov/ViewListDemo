package com.sultanseidov.viewlistdemo2.data.model.dto.tvshow

import com.sultanseidov.viewlistdemo2.domain.model.TvShowModel

data class TVShowDto(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)