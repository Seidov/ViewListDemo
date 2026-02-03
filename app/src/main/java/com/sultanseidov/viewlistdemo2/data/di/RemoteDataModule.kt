package com.sultanseidov.viewlistdemo2.data.di

import com.sultanseidov.viewlistdemo2.data.local.database.AppDatabase
import com.sultanseidov.viewlistdemo2.data.remote.api.ITMDBApi
import com.sultanseidov.viewlistdemo2.data.repository.dataSource.IRemoteDataSource
import com.sultanseidov.viewlistdemo2.data.repository.dataSourceImp.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Provides
    fun provideMoviesRemoteDataSource(tmdbApi: ITMDBApi, appDatabase: AppDatabase):
            IRemoteDataSource =
        RemoteDataSourceImpl(tmdbApi, appDatabase)

}