package com.sultanseidov.viewlistdemo2.data.repository

import androidx.paging.*
import com.sultanseidov.viewlistdemo2.BuildConfig
import com.sultanseidov.viewlistdemo2.data.local.database.AppDatabase
import com.sultanseidov.viewlistdemo2.data.model.base.ResourceState
import com.sultanseidov.viewlistdemo2.data.model.genre.GenresMovieModel
import com.sultanseidov.viewlistdemo2.data.model.genre.GenresTvShowModel
import com.sultanseidov.viewlistdemo2.data.model.genre.ResponseMovieGenresListModel
import com.sultanseidov.viewlistdemo2.data.model.genre.ResponseTVShowGenresListModel
import com.sultanseidov.viewlistdemo2.data.model.movie.MovieModel
import com.sultanseidov.viewlistdemo2.data.model.movie.MoviesRemoteKeys
import com.sultanseidov.viewlistdemo2.data.model.tvshow.TvShowModel
import com.sultanseidov.viewlistdemo2.data.model.tvshow.TvShowsRemoteKeys
import com.sultanseidov.viewlistdemo2.data.model.viewlist.ViewListMoviesModel
import com.sultanseidov.viewlistdemo2.data.model.viewlist.ViewListTvShowsModel
import com.sultanseidov.viewlistdemo2.data.model.pinviewlist.PinViewListModel
import com.sultanseidov.viewlistdemo2.data.paging.DiscoverMoviesRemoteMediator
import com.sultanseidov.viewlistdemo2.data.paging.DiscoverTvShowsRemoteMediator
import com.sultanseidov.viewlistdemo2.data.remote.ITmdbApi
import com.sultanseidov.viewlistdemo2.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RepositoryImpl @Inject constructor(
    private val iTmdbApi: ITmdbApi,
    private val appDatabase: AppDatabase
    ) : IRepository {


    //discoverMoviesDao()
    override fun getAllDiscoverMovies(with_genres: String): Flow<PagingData<MovieModel>> {
        val pagingSourceFactory = { appDatabase.discoverMoviesDao().getAllDiscoverMovies() }
        return Pager(
            config = PagingConfig(pageSize = Constants.ITEMS_PER_PAGE),
            remoteMediator = DiscoverMoviesRemoteMediator(iTmdbApi, appDatabase,with_genres),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
    override suspend fun insertDiscoverMovies(discoverMovies: List<MovieModel>) {
        appDatabase.discoverMoviesDao().insertDiscoverMovies(discoverMovies)
    }
    override suspend fun deleteAllDiscoverMovies() {
        appDatabase.discoverMoviesDao().deleteAllDiscoverMovies()
    }


    //discoverMoviesRemoteKeysDao
    override suspend fun getDiscoverMoviesRemoteKeys(id: Int): MoviesRemoteKeys {
        return appDatabase.discoverMoviesRemoteKeysDao().getRemoteKeys(id)
    }
    override suspend fun insertAllDiscoverMoviesRemoteKeys(remoteKeys: List<MoviesRemoteKeys>) {
        appDatabase.discoverMoviesRemoteKeysDao().insertAllRemoteKeys(remoteKeys)
    }
    override suspend fun deleteAllDiscoverMoviesRemoteKeys() {
        appDatabase.discoverMoviesRemoteKeysDao().deleteAllRemoteKeys()
    }



    //discoverTvShowsDao
    override fun getAllDiscoverTvShows(with_genres: String): Flow<PagingData<TvShowModel>> {
        val pagingSourceFactory = { appDatabase.discoverTvShowsDao().getAllDiscoverTvShows() }
        return Pager(
            config = PagingConfig(pageSize = Constants.ITEMS_PER_PAGE),
            remoteMediator = DiscoverTvShowsRemoteMediator(iTmdbApi, appDatabase, with_genres),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
    override suspend fun insertDiscoverTvShows(discoverTvShows: List<TvShowModel>) {
        appDatabase.discoverTvShowsDao().insertDiscoverTvShows(discoverTvShows)
    }
    override suspend fun deleteAllDiscoverTvShows() {
        appDatabase.discoverTvShowsDao().deleteAllDiscoverTvShows()
    }


    //discoverTvShowsRemoteKeysDao
    override suspend fun getDiscoverTvShowsRemoteKeys(id: Int): TvShowsRemoteKeys {
        return appDatabase.discoverTvShowsRemoteKeysDao().getRemoteKeys(id)
    }
    override suspend fun insertAllDiscoverTvShowsRemoteKeys(remoteKeys: List<TvShowsRemoteKeys>) {
        appDatabase.discoverTvShowsRemoteKeysDao().insertAllRemoteKeys(remoteKeys)
    }
    override suspend fun deleteAllDiscoverTvShowsRemoteKeys() {
        appDatabase.discoverTvShowsRemoteKeysDao().deleteAllRemoteKeys()
    }


    //genresMovieDao
    override fun getAllMovieGenres(): Flow<ResourceState<ResponseMovieGenresListModel>> = flow {
        try {
            emit(ResourceState.Loading)
            val responseApi = iTmdbApi.getMovieGenresList(
                api_key = BuildConfig.API_KEY
            )
            emit(ResourceState.Success(responseApi))
        } catch (e: Exception) {
            emit(ResourceState.Error(e))
        }
    }.flowOn(Dispatchers.IO)
    override suspend fun insertMovieGenres(genres: List<GenresMovieModel>) {
        appDatabase.genresMovieDao().insertGenres(genres)
    }
    override suspend fun deleteAllMovieGenres() {
        appDatabase.genresMovieDao().deleteAllGenres()
    }


    //genresTvShowDao
    override fun getAllTvShowGenres(): Flow<ResourceState<ResponseTVShowGenresListModel>> = flow {
        try {
            emit(ResourceState.Loading)
            val responseApi = iTmdbApi.getTVShowGenresList(
                api_key = BuildConfig.API_KEY
            )
            emit(ResourceState.Success(responseApi))
        } catch (e: Exception) {
            emit(ResourceState.Error(e))
        }
    }.flowOn(Dispatchers.IO)
    override suspend fun insertTvShowGenres(genres: List<GenresTvShowModel>) {
        appDatabase.genresTvShowDao().insertGenres(genres)
    }
    override suspend fun deleteAllTvShowGenres() {
        appDatabase.genresTvShowDao().deleteAllGenres()
    }


    //viewListMoviesDao
    override fun getMovieViewListById(movieId: Int): Flow<List<ViewListMoviesModel>> {
        return appDatabase.viewListMoviesDao().getMovieById(movieId)
    }
    override fun getAllMoviesViewList(): Flow<List<ViewListMoviesModel>> {
        return appDatabase.viewListMoviesDao().getAllMovies()
    }
    override suspend fun insertMovieViewList(movieViewList: ViewListMoviesModel) {
        appDatabase.viewListMoviesDao().insertMovie(movieViewList)
    }
    override suspend fun insertMoviesViewList(movieViewList: List<MovieModel>) {
        appDatabase.viewListMoviesDao().insertMovies(movieViewList)
    }
    override suspend fun updateMovieViewList(movieViewList: ViewListMoviesModel) {
        appDatabase.viewListMoviesDao().updateMovie(movieViewList)
    }
    override suspend fun deleteAllMovieViewList() {
        appDatabase.viewListMoviesDao().deleteAllMovies()
    }


    //viewListTvShowsDao
    override fun getTvShowViewListById(tvShowId: Int): Flow<List<ViewListTvShowsModel>> {
        return appDatabase.viewListTvShowsDao().getTvShowById(tvShowId)
    }
    override fun getAllTvShowsViewList(): Flow<List<ViewListTvShowsModel>> {
        return appDatabase.viewListTvShowsDao().getAllTvShows()
    }
    override suspend fun insertTvShowViewList(tvShowViewList: ViewListTvShowsModel) {
        appDatabase.viewListTvShowsDao().insertTvShow(tvShowViewList)
    }
    override suspend fun insertTvShowsViewList(tvShowViewList: List<TvShowModel>) {
        appDatabase.viewListTvShowsDao().insertTvShows(tvShowViewList)
    }
    override suspend fun updateTvShowViewList(tvShowViewList: ViewListTvShowsModel) {
        appDatabase.viewListTvShowsDao().updateTvShow(tvShowViewList)
    }
    override suspend fun deleteAllTvShowViewList() {
        appDatabase.viewListTvShowsDao().deleteAllTvShows()
    }


    //pinViewListDao
    override fun getAllPinsViewList(): Flow<ResourceState<Flow<List<PinViewListModel>>>> = flow {
        try {
            emit(ResourceState.Loading)
            emit(ResourceState.Success(appDatabase.pinViewListDao().getAllPins()))
        } catch (e: Exception) {
            emit(ResourceState.Error(e))
        }
    }.flowOn(Dispatchers.IO)
    override suspend fun insertPinViewList(pin: PinViewListModel) {
        appDatabase.pinViewListDao().insertPin(pin)
    }
    override suspend fun insertPinsViewList(pins: List<PinViewListModel>) {
        appDatabase.pinViewListDao().insertPins(pins)
    }
    override suspend fun updatePinViewList(pin: PinViewListModel) {
        appDatabase.pinViewListDao().updatePin(pin)
    }
    override suspend fun deleteAllPinsViewList() {
        appDatabase.pinViewListDao().deleteAllPins()
    }

}