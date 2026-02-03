package com.sultanseidov.viewlistdemo2.domain.usecase.genre

import com.sultanseidov.viewlistdemo2.domain.usecase.discover.GetDiscoverMoviesUseCase
import com.sultanseidov.viewlistdemo2.domain.usecase.discover.GetDiscoverTVShowsUseCase

data class GenresUseCase(
    val getDiscoverMoviesUseCase: GetDiscoverMoviesUseCase,
    val getDiscoverTVShowsUseCase: GetDiscoverTVShowsUseCase
)