package com.sultanseidov.viewlistdemo2.data.di

import com.sultanseidov.viewlistdemo2.data.local.database.AppDatabase
import com.sultanseidov.viewlistdemo2.data.remote.api.ITMDBApi
import com.sultanseidov.viewlistdemo2.data.repository.MovieRepositoryImpl
import com.sultanseidov.viewlistdemo2.domain.repository.IMovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        iTmdbApi: ITMDBApi,
        appDatabase: AppDatabase
    ): IMovieRepository = MovieRepositoryImpl(iTmdbApi, appDatabase)
}
