package com.sultanseidov.viewlistdemo2.data.remote.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.sultanseidov.viewlistdemo2.BuildConfig.API_KEY
import com.sultanseidov.viewlistdemo2.data.local.database.AppDatabase
import com.sultanseidov.viewlistdemo2.data.model.dto.old.DiscoveryRemoteKeys
import com.sultanseidov.viewlistdemo2.data.model.mappers.movie.toMovieList
import com.sultanseidov.viewlistdemo2.data.remote.api.ITMDBApi
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.util.CyberLogger
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class DiscoverMoviesRemoteMediator @Inject constructor(
    private val iTmdbApi: ITMDBApi,
    private val appDatabase: AppDatabase,
    private val with_genres: String
) : RemoteMediator<Int, MovieModel>() {

    private val popularMoviesDao = appDatabase.discoverMoviesDao()
    private val discoveryRemoteKeysDao = appDatabase.discoveryRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieModel>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextPage
                }
            }

            val response = CyberLogger.traceNetworkExecution("FETCH_DISCOVER_MOVIES_PAGE_$currentPage") {
                iTmdbApi.getDiscoverMovies(
                    api_key = API_KEY,
                    page = currentPage,
                    with_genres = with_genres
                )
            }

            if (response.isSuccessful) {
                val responseData = response.body()
                val movies = responseData?.toMovieList() ?: emptyList()
                val endOfPaginationReached = movies.isEmpty() || (responseData?.page ?: 0) >= (responseData?.total_pages ?: 0)

                appDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        popularMoviesDao.deleteAllDiscoverMovies()
                        discoveryRemoteKeysDao.deleteAllRemoteKeysByType("MOVIE")
                    }
                    
                    val prevPage = if (currentPage <= 1) null else currentPage - 1
                    val nextPage = if (endOfPaginationReached) null else currentPage + 1

                    val keys = movies.map { movie ->
                        DiscoveryRemoteKeys(
                            id = movie.id,
                            type = "MOVIE",
                            prevPage = prevPage,
                            nextPage = nextPage,
                            lastUpdated = System.currentTimeMillis()
                        )
                    }

                    popularMoviesDao.insertDiscoverMovies(
                        discoverMovies = movies.map { it.copy(page = currentPage) }
                    )
                    discoveryRemoteKeysDao.insertAllRemoteKeys(remoteKeys = keys)
                }
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } else {
                MediatorResult.Error(Exception("Network Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieModel>,
    ): DiscoveryRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                discoveryRemoteKeysDao.getRemoteKeys(id = movie.id, type = "MOVIE")
            }
    }
}
