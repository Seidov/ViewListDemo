package com.sultanseidov.viewlistdemo2.di

import com.sultanseidov.viewlistdemo2.data.local.database.AppDatabase
import com.sultanseidov.viewlistdemo2.data.remote.ITMDBApi
import com.sultanseidov.viewlistdemo2.domain.repository.IRepository
import com.sultanseidov.viewlistdemo2.data.repository.RepositoryImpl
import com.sultanseidov.viewlistdemo2.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTmdbApi(retrofit: Retrofit): ITMDBApi {
        return retrofit.create(ITMDBApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(iTmdbApi: ITMDBApi, appDatabase: AppDatabase): IRepository {
        return RepositoryImpl(iTmdbApi = iTmdbApi, appDatabase = appDatabase)
    }
}
