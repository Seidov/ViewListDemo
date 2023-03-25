package com.sultanseidov.viewlistdemo2.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sultanseidov.viewlistdemo2.BuildConfig.API_KEY
import com.sultanseidov.viewlistdemo2.data.entity.genre.GenreModel
import com.sultanseidov.viewlistdemo2.data.entity.genre.ResponseGenresListModel
import com.sultanseidov.viewlistdemo2.data.entity.base.ResourceState
import com.sultanseidov.viewlistdemo2.data.entity.movie.MovieModel
import com.sultanseidov.viewlistdemo2.data.entity.tvshow.TvShowModel
import com.sultanseidov.viewlistdemo2.data.local.database.AppDatabase
import com.sultanseidov.viewlistdemo2.data.paging.DiscoverMoviesRemoteMediator
import com.sultanseidov.viewlistdemo2.data.paging.DiscoverTvShowsRemoteMediator
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
    fun getDiscoverMovies(with_genres:String): Flow<PagingData<MovieModel>> {
        val pagingSourceFactory = { appDatabase.discoverMoviesDao().getAllDiscoverMovies() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = DiscoverMoviesRemoteMediator(iTmdbApi, appDatabase,with_genres),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
    fun getDiscoverTvShows(with_genres:String): Flow<PagingData<TvShowModel>> {
        val pagingSourceFactory = { appDatabase.discoverTvShowsDao().getAllDiscoverTvShows() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = DiscoverTvShowsRemoteMediator(iTmdbApi, appDatabase,with_genres),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }


}