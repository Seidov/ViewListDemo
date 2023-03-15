package com.sultanseidov.viewlistdemo2.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sultanseidov.viewlistdemo2.BuildConfig.API_KEY
import com.sultanseidov.viewlistdemo2.data.entity.genre.GenreModel
import com.sultanseidov.viewlistdemo2.data.entity.genre.ResponseGenresListModel
import com.sultanseidov.viewlistdemo2.data.entity.base.ResourceState
import com.sultanseidov.viewlistdemo2.data.entity.movie.PopularMoviesModel
import com.sultanseidov.viewlistdemo2.data.local.database.AppDatabase
import com.sultanseidov.viewlistdemo2.data.paging.PopularMoviesRemoteMediator
import com.sultanseidov.viewlistdemo2.data.remote.ITmdbApi
import com.sultanseidov.viewlistdemo2.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class Repository @Inject constructor(
    private val iTmdbApi: ITmdbApi,
    private val appDatabase: AppDatabase
) {

    val getAllGenres: Flow<List<GenreModel>> = appDatabase.genresDao().getAllGenres()

    suspend fun addGenresList(genres: List<GenreModel>){
        appDatabase.genresDao().addGenres(genres)
    }

    fun getPopularMovies(with_genres:String): Flow<PagingData<PopularMoviesModel>> {
        val pagingSourceFactory = { appDatabase.popularMoviesDao().getAllPopularMovies() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = PopularMoviesRemoteMediator(iTmdbApi, appDatabase,with_genres),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getPopularMovies2(with_genres:String): Flow<PagingData<PopularMoviesModel>> {
        val pagingSourceFactory = { appDatabase.popularMoviesDao().getAllPopularMovies() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = PopularMoviesRemoteMediator(iTmdbApi, appDatabase,with_genres),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getPopularMovies3(with_genres:String): Flow<PagingData<PopularMoviesModel>> {
        val pagingSourceFactory = { appDatabase.popularMoviesDao().getAllPopularMovies() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = PopularMoviesRemoteMediator(iTmdbApi, appDatabase,with_genres),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getMovieGenres(): Flow<ResourceState<ResponseGenresListModel>> = flow{
        try {
            emit(ResourceState.Loading)
            val responseApi = iTmdbApi.getMovieGenresList(
                api_key = API_KEY
            )
            emit(ResourceState.Success(responseApi))
        } catch (e: Exception) {
            emit(ResourceState.Error(e))
        }
    }.flowOn(Dispatchers.IO)

}