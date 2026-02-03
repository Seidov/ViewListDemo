package com.sultanseidov.viewlistdemo2.domain.di

import com.sultanseidov.viewlistdemo2.domain.repository.INewRepository
import com.sultanseidov.viewlistdemo2.domain.usecase.discover.DiscoverUseCase
import com.sultanseidov.viewlistdemo2.domain.usecase.discover.GetDiscoverMoviesUseCase
import com.sultanseidov.viewlistdemo2.domain.usecase.discover.GetDiscoverTVShowsUseCase
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
    fun provideRepoUseCases(repository: INewRepository) = DiscoverUseCase(
        getDiscoverMoviesUseCase = GetDiscoverMoviesUseCase(repository = repository),
        getDiscoverTVShowsUseCase = GetDiscoverTVShowsUseCase(repository = repository)
    )
}
