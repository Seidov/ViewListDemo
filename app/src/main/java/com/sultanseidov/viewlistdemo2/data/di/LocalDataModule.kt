package com.sultanseidov.viewlistdemo2.data.di

import com.sultanseidov.viewlistdemo2.data.local.database.AppDatabase
import com.sultanseidov.viewlistdemo2.data.repository.dataSource.ILocalDataSource
import com.sultanseidov.viewlistdemo2.data.repository.dataSourceImp.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    fun provideLocalDataSource(appDataBase: AppDatabase):
            ILocalDataSource =
        LocalDataSourceImpl(appDatabase = appDataBase)

}