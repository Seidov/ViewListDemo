package com.sultanseidov.viewlistdemo2.data.paging


import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.sultanseidov.viewlistdemo2.BuildConfig.API_KEY
import com.sultanseidov.viewlistdemo2.data.entity.movie.PopularMoviesModel
import com.sultanseidov.viewlistdemo2.data.entity.movie.PopularMoviesRemoteKeys
import com.sultanseidov.viewlistdemo2.data.entity.myviewlist.MyViewListModel
import com.sultanseidov.viewlistdemo2.data.local.database.AppDatabase
import com.sultanseidov.viewlistdemo2.data.remote.ITmdbApi
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PopularMoviesRemoteMediator @Inject constructor(
    private val iTmdbApi: ITmdbApi,
    private val appDatabase: AppDatabase,
    private val with_genres: String
) : RemoteMediator<Int, PopularMoviesModel>() {


    private val myViewListDao = appDatabase.myViewListDao()
    private val popularMoviesDao = appDatabase.popularMoviesDao()
    private val popularMoviesRemoteKeysDao = appDatabase.popularMoviesRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        // Require that remote REFRESH is launched on initial load and succeeds before launching
        // remote PREPEND / APPEND.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PopularMoviesModel>
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

            val response = iTmdbApi.getPopularMovies(
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
                            popularMoviesDao.deleteAllPopularMovies()
                            popularMoviesRemoteKeysDao.deleteAllRemoteKeys()
                        }
                        var prevPage: Int?
                        var nextPage: Int

                        responseData.page.let { pageNumber ->
                            nextPage = pageNumber + 1
                            prevPage = if (pageNumber <= 1) null else pageNumber - 1
                        }

                        val keys = responseData.movies?.map { movie ->
                            PopularMoviesRemoteKeys(
                                id = movie.movieId,
                                prevPage = prevPage,
                                nextPage = nextPage,
                                lastUpdated = System.currentTimeMillis()
                            )
                        }

                        popularMoviesRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys!!)

                        Log.e("FUCK", "popularMoviesRemoteKeysDao has got and declared!!" )

                        popularMoviesDao.addPopularMovies(popularMovies = responseData.movies)

                        Log.e("FUCK", "popularMoviesDao has got and declared!!" )

                        //myViewListDao.addAllMyListView(myViewList = responseData.movies)
                        Log.e("FUCK", "myViewListDao has got and declared! : " + myViewListDao.getAllMyListView() )
                    }
                }

            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }

        Log.e("FUCK", "load method finished has got and declared!!" )
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PopularMoviesModel>,
    ): PopularMoviesRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.movieId?.let { id ->
                popularMoviesRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PopularMoviesModel>,
    ): PopularMoviesRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                popularMoviesRemoteKeysDao.getRemoteKeys(id = movie.movieId)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, PopularMoviesModel>,
    ): PopularMoviesRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                popularMoviesRemoteKeysDao.getRemoteKeys(id = movie.movieId)
            }
    }


}