package com.sultanseidov.viewlistdemo2.data.di

import android.content.Context
import androidx.room.Room
import com.sultanseidov.viewlistdemo2.data.local.dao.PinDao
import com.sultanseidov.viewlistdemo2.data.local.dao.WatchedMovieDao
import com.sultanseidov.viewlistdemo2.data.local.dao.DiscoveryRemoteKeysDao
import com.sultanseidov.viewlistdemo2.data.local.database.AppDatabase
import com.sultanseidov.viewlistdemo2.util.Constants.APP_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, APP_DATABASE)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideWatchedMovieDao(database: AppDatabase): WatchedMovieDao {
        return database.watchedMovieDao()
    }

    @Provides
    @Singleton
    fun providePinDao(database: AppDatabase): PinDao {
        return database.pinDao()
    }

    @Provides
    @Singleton
    fun provideDiscoveryRemoteKeysDao(database: AppDatabase): DiscoveryRemoteKeysDao {
        return database.discoveryRemoteKeysDao()
    }

}
