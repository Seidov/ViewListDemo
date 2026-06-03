package com.sultanseidov.viewlistdemo2.data.model.mappers.movie

import com.sultanseidov.viewlistdemo2.data.model.dto.movie.MoviesDto
import com.sultanseidov.viewlistdemo2.data.model.dto.movie.Result
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel

fun MoviesDto.toMovieList(): List<MovieModel> {
    return results.map { it.toMovieModel() }
}

fun Result.toMovieModel(): MovieModel {
    val finalGenreIds = genre_ids ?: genres?.map { it.id }
    return MovieModel(
        id,
        adult,
        backdrop_path,
        finalGenreIds,
        original_language,
        original_title,
        overview,
        popularity,
        poster_path,
        release_date,
        title,
        video,
        vote_average,
        vote_count
    )
}
