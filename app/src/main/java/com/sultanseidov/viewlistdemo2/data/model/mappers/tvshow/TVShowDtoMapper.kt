package com.sultanseidov.viewlistdemo2.data.model.mappers.tvshow

import com.sultanseidov.viewlistdemo2.data.model.dto.tvshow.TVShowDto
import com.sultanseidov.viewlistdemo2.domain.model.TvShowModel

fun TVShowDto.toTVShowList(): List<TvShowModel> {
    return results.map { result ->
        TvShowModel(
            result.backdrop_path,
            result.first_air_date,
            result.genre_ids,
            result.id,
            result.name,
            result.original_language,
            result.original_name,
            result.overview,
            result.popularity,
            result.poster_path,
            result.vote_average,
            result.vote_count
        )
    }
}