package com.sultanseidov.viewlistdemo2.data.paging


import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.sultanseidov.viewlistdemo2.BuildConfig.API_KEY
import com.sultanseidov.viewlistdemo2.data.entity.movie.MoviesRemoteKeys
import com.sultanseidov.viewlistdemo2.data.entity.tvshow.TvShowModel
import com.sultanseidov.viewlistdemo2.data.entity.tvshow.TvShowsRemoteKeys
import com.sultanseidov.viewlistdemo2.data.local.database.AppDatabase
import com.sultanseidov.viewlistdemo2.data.remote.ITmdbApi
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class DiscoverTvShowsRemoteMediator @Inject constructor(
    private val iTmdbApi: ITmdbApi,
    private val appDatabase: AppDatabase,
    private val with_genres: String
) : RemoteMediator<Int, TvShowModel>() {


    private val myViewListDao = appDatabase.myViewListDao()
    private val discoverTvShowsDao = appDatabase.discoverTvShowsDao()
    private val discoverTvShowsRemoteKeysDao = appDatabase.discoverTvShowsRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        // Require that remote REFRESH is launched on initial load and succeeds before launching
        // remote PREPEND / APPEND.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TvShowModel>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = iTmdbApi.getDiscoverTvShows(
                api_key = API_KEY,
                page = currentPage,
                with_genres = with_genres
            )

            var endOfPaginationReached = false
            if (response.isSuccessful) {
                val responseData = response.body()
                endOfPaginationReached = responseData == null
                responseData?.let {
                    appDatabase.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            discoverTvShowsDao.deleteAllDiscoverTvShows()
                            discoverTvShowsRemoteKeysDao.deleteAllRemoteKeys()
                        }
                        var prevPage: Int?
                        var nextPage: Int

                        responseData.page.let { pageNumber ->
                            nextPage = pageNumber + 1
                            prevPage = if (pageNumber <= 1) null else pageNumber - 1
                        }

                        val keys = responseData.tvShowModels?.map { tvShowModel ->
                            TvShowsRemoteKeys(
                                id = tvShowModel.tvShowId,
                                prevPage = prevPage,
                                nextPage = nextPage,
                                lastUpdated = System.currentTimeMillis()
                            )
                        }

                        discoverTvShowsDao.addDiscoverTvShows(discoverTvShows = responseData.tvShowModels)
                        discoverTvShowsRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys!!)
                    }
                }

            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: Exception) {
            Log.e("DiscoverTvShowsRemoteMediator", e.message.toString())
            return MediatorResult.Error(e)

        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, TvShowModel>,
    ): TvShowsRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.tvShowId?.let { id ->
                discoverTvShowsRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, TvShowModel>,
    ): TvShowsRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { tvShowsModel ->
                discoverTvShowsRemoteKeysDao.getRemoteKeys(id = tvShowsModel.tvShowId)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, TvShowModel>,
    ): TvShowsRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { tvShowsModel ->
                discoverTvShowsRemoteKeysDao.getRemoteKeys(id = tvShowsModel.tvShowId)
            }
    }


}