package com.sultanseidov.viewlistdemo2.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sultanseidov.viewlistdemo2.data.entity.MovieModel
import com.sultanseidov.viewlistdemo2.data.local.AppDatabase
import com.sultanseidov.viewlistdemo2.data.paging.PopularMoviesRemoteMediator
import com.sultanseidov.viewlistdemo2.data.remote.ITmdbApi
import com.sultanseidov.viewlistdemo2.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class Repository @Inject constructor(
    private val iTmdbApi: ITmdbApi,
    private val appDatabase: AppDatabase
) {

    fun getPopularMovies(): Flow<PagingData<MovieModel>> {
        val pagingSourceFactory = { appDatabase.popularMoviesDao().getAllPopularMovies() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = PopularMoviesRemoteMediator(iTmdbApi, appDatabase),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

}