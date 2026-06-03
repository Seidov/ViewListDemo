package com.sultanseidov.viewlistdemo2.domain.di

import com.sultanseidov.viewlistdemo2.data.local.dao.PinDao
import com.sultanseidov.viewlistdemo2.data.local.dao.WatchedMovieDao
import com.sultanseidov.viewlistdemo2.domain.repository.IMovieRepository
import com.sultanseidov.viewlistdemo2.domain.usecase.discover.DiscoverUseCase
import com.sultanseidov.viewlistdemo2.domain.usecase.discover.GetDiscoverMoviesUseCase
import com.sultanseidov.viewlistdemo2.domain.usecase.discover.GetDiscoverTVShowsUseCase
import com.sultanseidov.viewlistdemo2.domain.usecase.pin.WatchClassificationPipelineUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi


@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideRepoUseCases(repository: IMovieRepository) = DiscoverUseCase(
        getDiscoverMoviesUseCase = GetDiscoverMoviesUseCase(repository = repository),
        getDiscoverTVShowsUseCase = GetDiscoverTVShowsUseCase(repository = repository)
    )

    @Provides
    fun provideWatchClassificationPipelineUseCase(
        repository: IMovieRepository,
        watchedMovieDao: WatchedMovieDao,
        pinDao: PinDao
    ) = WatchClassificationPipelineUseCase(
        repository = repository,
        watchedMovieDao = watchedMovieDao,
        pinDao = pinDao
    )
}
