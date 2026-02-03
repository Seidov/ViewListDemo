package com.sultanseidov.viewlistdemo2.data.model.dto.genre

import com.sultanseidov.viewlistdemo2.data.model.dto.movie.MoviesDto
import com.sultanseidov.viewlistdemo2.domain.model.GenreMovieModel
import com.sultanseidov.viewlistdemo2.domain.model.GenreTvShowModel
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel

data class GenresDto(
    val genres: List<Genre>
)