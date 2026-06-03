package com.sultanseidov.viewlistdemo2.data.remote.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.sultanseidov.viewlistdemo2.BuildConfig.API_KEY
import com.sultanseidov.viewlistdemo2.data.local.database.AppDatabase
import com.sultanseidov.viewlistdemo2.data.local.entity.DiscoveryMediaEntity
import com.sultanseidov.viewlistdemo2.data.local.entity.DiscoveryRemoteKeysEntity
import com.sultanseidov.viewlistdemo2.data.model.dto.BaseDiscoveryItem
import com.sultanseidov.viewlistdemo2.data.remote.api.ITMDBApi
import com.sultanseidov.viewlistdemo2.util.CyberLogger

@OptIn(ExperimentalPagingApi::class)
class UnifiedDiscoveryRemoteMediator(
    private val api: ITMDBApi,
    private val database: AppDatabase,
    private val categoryTag: String,
    private val mediaType: String, // "MOVIE" or "TV"
    private val genres: String
) : RemoteMediator<Int, DiscoveryMediaEntity>() {

    private val discoveryDao = database.discoveryDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DiscoveryMediaEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    remoteKeys?.nextPage ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
            }

            var totalPages = 1
            val items: List<BaseDiscoveryItem> = CyberLogger.traceNetworkExecution("FETCH_${categoryTag}_PAGE_$page") {
                if (mediaType == "MOVIE") {
                    val response = api.getDiscoverMovies(API_KEY, page, with_genres = genres)
                    if (response.isSuccessful) {
                        totalPages = response.body()?.total_pages ?: 1
                        response.body()?.results?.map {
                            BaseDiscoveryItem(it.id, it.title, it.poster_path, it.vote_average)
                        } ?: emptyList()
                    } else throw Exception("API_ERROR_${response.code()}")
                } else {
                    val response = api.getDiscoverTvShows(API_KEY, page, with_genres = genres)
                    if (response.isSuccessful) {
                        totalPages = response.body()?.total_pages ?: 1
                        response.body()?.results?.map {
                            BaseDiscoveryItem(it.id, it.name, it.poster_path, it.vote_average)
                        } ?: emptyList()
                    } else throw Exception("API_ERROR_${response.code()}")
                }
            }

            val isEnd = items.isEmpty() || page >= totalPages

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    discoveryDao.clearCacheByTag(categoryTag)
                    discoveryDao.clearRemoteKeysByTag(categoryTag)
                }

                val prevPage = if (page == 1) null else page - 1
                val nextPage = if (isEnd) null else page + 1

                val entities = items.mapIndexed { index, item ->
                    DiscoveryMediaEntity(
                        id = item.id,
                        categoryTag = categoryTag,
                        title = item.title,
                        posterPath = item.posterPath,
                        mediaType = mediaType,
                        page = page,
                        indexInPage = index,
                        voteAverage = item.voteAverage
                    )
                }

                val keys = items.map {
                    DiscoveryRemoteKeysEntity(
                        id = it.id,
                        categoryTag = categoryTag,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                discoveryDao.insertAll(entities)
                discoveryDao.insertRemoteKeys(keys)
            }
            MediatorResult.Success(endOfPaginationReached = isEnd)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, DiscoveryMediaEntity>): DiscoveryRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {
            discoveryDao.getRemoteKeys(it.id, categoryTag)
        }
    }
}
