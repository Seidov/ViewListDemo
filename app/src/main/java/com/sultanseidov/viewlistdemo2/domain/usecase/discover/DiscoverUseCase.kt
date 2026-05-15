package com.sultanseidov.viewlistdemo2.domain.usecase.discover

data class DiscoverUseCase(
    val getDiscoverMoviesUseCase: GetDiscoverMoviesUseCase,
    val getDiscoverTVShowsUseCase: GetDiscoverTVShowsUseCase
)