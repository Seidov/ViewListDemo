package com.sultanseidov.viewlistdemo2.data.di

import com.sultanseidov.viewlistdemo2.data.repository.NewRepositoryImpl
import com.sultanseidov.viewlistdemo2.data.repository.dataSource.ILocalDataSource
import com.sultanseidov.viewlistdemo2.data.repository.dataSource.IRemoteDataSource
import com.sultanseidov.viewlistdemo2.domain.repository.INewRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideMoviesRepository(
        remoteDataSource: IRemoteDataSource,
        localDataSource: ILocalDataSource
    ): INewRepository =
        NewRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource
        )
}