package com.sultanseidov.viewlistdemo2.data.repository.dataSourceImp

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sultanseidov.viewlistdemo2.BuildConfig.API_KEY
import com.sultanseidov.viewlistdemo2.data.local.database.AppDatabase
import com.sultanseidov.viewlistdemo2.data.model.base.ResourceState
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresDto
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresMovieModel
import com.sultanseidov.viewlistdemo2.data.model.mappers.genre.toMovieGenresList
import com.sultanseidov.viewlistdemo2.data.remote.api.ITMDBApi
import com.sultanseidov.viewlistdemo2.data.remote.paging.DiscoverMoviesRemoteMediator
import com.sultanseidov.viewlistdemo2.data.remote.paging.DiscoverTvShowsRemoteMediator
import com.sultanseidov.viewlistdemo2.data.repository.dataSource.IRemoteDataSource
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.domain.model.TvShowModel
import com.sultanseidov.viewlistdemo2.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val iTmdbApi: ITMDBApi,
    private val appDatabase: AppDatabase
) : IRemoteDataSource {

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllDiscoverMovies(with_genres: String): Flow<PagingData<MovieModel>> {
        val pagingSourceFactory = { appDatabase.discoverMoviesDao().getAllDiscoverMovies() }
        return Pager(
            config = PagingConfig(pageSize = Constants.ITEMS_PER_PAGE),
            remoteMediator = DiscoverMoviesRemoteMediator(iTmdbApi, appDatabase, with_genres),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllDiscoverTvShows(with_genres: String): Flow<PagingData<TvShowModel>> {
        val pagingSourceFactory = { appDatabase.discoverTvShowsDao().getAllDiscoverTvShows() }
        return Pager(
            config = PagingConfig(pageSize = Constants.ITEMS_PER_PAGE),
            remoteMediator = DiscoverTvShowsRemoteMediator(iTmdbApi, appDatabase, with_genres),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun getAllMovieGenres(): Flow<ResourceState<List<GenresMovieModel>>> = flow {
        emit(ResourceState.Loading())
        try {
            emit(ResourceState.Success(iTmdbApi.getMovieGenresList(API_KEY).toMovieGenresList()))
        } catch (e: Exception) {
            emit(ResourceState.Error("Unknown Error"))
        }

    }.flowOn(Dispatchers.IO)

    override suspend fun getAllTvShowGenres(): Flow<ResourceState<GenresDto>> = flow {
        emit(ResourceState.Loading())
        try {
            emit(ResourceState.Success(iTmdbApi.getTVShowGenresList(API_KEY)))
        } catch (e: Exception) {
            emit(ResourceState.Error("Unknown Error"))
        }

    }.flowOn(Dispatchers.IO)

}