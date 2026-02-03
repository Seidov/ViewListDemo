package com.sultanseidov.viewlistdemo2.data.model.mappers.movie

import com.sultanseidov.viewlistdemo2.data.model.dto.movie.MoviesDto
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel

fun MoviesDto.toMovieList(): List<MovieModel> {
    return results.map { result ->
        MovieModel(
            result.id,
            result.adult,
            result.backdrop_path,
            result.genre_ids,
            result.original_language,
            result.original_title,
            result.overview,
            result.popularity,
            result.poster_path,
            result.release_date,
            result.title,
            result.video,
            result.vote_average,
            result.vote_count
        )
    }
}