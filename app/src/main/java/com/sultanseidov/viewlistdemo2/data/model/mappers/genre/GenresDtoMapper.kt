package com.sultanseidov.viewlistdemo2.data.model.mappers.genre

import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresDto
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresMovieModel
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresTvShowModel
import com.sultanseidov.viewlistdemo2.domain.model.GenreMovieModel
import com.sultanseidov.viewlistdemo2.domain.model.GenreTvShowModel


fun GenresDto.toMovieGenresList(): List<GenresMovieModel> {
    return genres.map { genres ->
        GenresMovieModel(
            genres.id,
            genres.name
        )
    }
}

fun GenresDto.toTvShowGenresList(): List<GenresTvShowModel> {
    return genres.map { genres ->
        GenresTvShowModel(
            genres.id,
            genres.name
        )
    }
}