package com.sultanseidov.viewlistdemo2.data.repository

import androidx.paging.*
import com.sultanseidov.viewlistdemo2.BuildConfig
import com.sultanseidov.viewlistdemo2.data.local.database.AppDatabase
import com.sultanseidov.viewlistdemo2.data.local.entity.DiscoveryMediaEntity
import com.sultanseidov.viewlistdemo2.data.model.base.ResourceState
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresDto
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresMovieModel
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresTvShowModel
import com.sultanseidov.viewlistdemo2.data.model.dto.old.MoviesRemoteKeys
import com.sultanseidov.viewlistdemo2.data.model.dto.old.TvShowsRemoteKeys
import com.sultanseidov.viewlistdemo2.data.model.mappers.genre.toMovieGenresList
import com.sultanseidov.viewlistdemo2.data.model.mappers.movie.toMovieModel
import com.sultanseidov.viewlistdemo2.data.model.pinviewlist.PinViewListModel
import com.sultanseidov.viewlistdemo2.data.remote.api.ITMDBApi
import com.sultanseidov.viewlistdemo2.data.remote.paging.DiscoverMoviesRemoteMediator
import com.sultanseidov.viewlistdemo2.data.remote.paging.DiscoverTvShowsRemoteMediator
import com.sultanseidov.viewlistdemo2.data.remote.paging.UnifiedDiscoveryRemoteMediator
import com.sultanseidov.viewlistdemo2.data.remote.paging.SearchMoviesPagingSource
import com.sultanseidov.viewlistdemo2.domain.model.*
import com.sultanseidov.viewlistdemo2.domain.repository.IMovieRepository
import com.sultanseidov.viewlistdemo2.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRepositoryImpl @Inject constructor(
    private val iTmdbApi: ITMDBApi,
    private val appDatabase: AppDatabase
) : IMovieRepository {

    // Discovery
    override fun getAllDiscoverMovies(with_genres: String?): Flow<PagingData<MovieModel>> {
        val pagingSourceFactory = { appDatabase.discoverMoviesDao().getAllDiscoverMovies() }
        return Pager(
            config = PagingConfig(pageSize = Constants.ITEMS_PER_PAGE),
            remoteMediator = DiscoverMoviesRemoteMediator(iTmdbApi, appDatabase, with_genres ?: ""),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun getAllDiscoverTvShows(with_genres: String): Flow<PagingData<TvShowModel>> {
        val pagingSourceFactory = { appDatabase.discoverTvShowsDao().getAllDiscoverTvShows() }
        return Pager(
            config = PagingConfig(pageSize = Constants.ITEMS_PER_PAGE),
            remoteMediator = DiscoverTvShowsRemoteMediator(iTmdbApi, appDatabase, with_genres),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun getDiscoveryFlowByTag(
        tag: String,
        mediaType: String,
        genres: String
    ): Flow<PagingData<DiscoveryMediaEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            remoteMediator = UnifiedDiscoveryRemoteMediator(iTmdbApi, appDatabase, tag, mediaType, genres),
            pagingSourceFactory = { appDatabase.discoveryDao().getPagingSourceByTag(tag) }
        ).flow
    }

    override fun searchMovies(query: String): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.ITEMS_PER_PAGE),
            pagingSourceFactory = { SearchMoviesPagingSource(iTmdbApi, query) }
        ).flow
    }

    // Remote Details
    override suspend fun getMovieDetails(movieId: Long): MovieModel {
        return iTmdbApi.getMovieDetails(movieId, BuildConfig.API_KEY).toMovieModel()
    }

    override suspend fun getMovieKeywords(movieId: Long): List<String> {
        return iTmdbApi.getMovieKeywords(movieId, BuildConfig.API_KEY).keywords.map { it.name }
    }

    override suspend fun getMovieGenres(): Flow<ResourceState<List<GenresMovieModel>>> = flow {
        emit(ResourceState.Loading())
        try {
            val response = iTmdbApi.getMovieGenresList(BuildConfig.API_KEY)
            emit(ResourceState.Success(response.toMovieGenresList()))
        } catch (e: Exception) {
            emit(ResourceState.Error(e.message ?: "Unknown Error"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getTvShowGenres(): Flow<ResourceState<GenresDto>> = flow {
        emit(ResourceState.Loading())
        try {
            emit(ResourceState.Success(iTmdbApi.getTVShowGenresList(BuildConfig.API_KEY)))
        } catch (e: Exception) {
            emit(ResourceState.Error(e.message ?: "Unknown Error"))
        }
    }.flowOn(Dispatchers.IO)

    // Local Storage - Discover Cache
    override suspend fun insertDiscoverMovies(discoverMovies: List<MovieModel>) {
        appDatabase.discoverMoviesDao().insertDiscoverMovies(discoverMovies)
    }

    override suspend fun deleteAllDiscoverMovies() {
        appDatabase.discoverMoviesDao().deleteAllDiscoverMovies()
    }

    override suspend fun insertDiscoverTvShows(discoverTvShows: List<TvShowModel>) {
        appDatabase.discoverTvShowsDao().insertDiscoverTvShows(discoverTvShows)
    }

    override suspend fun deleteAllDiscoverTvShows() {
        appDatabase.discoverTvShowsDao().deleteAllDiscoverTvShows()
    }

    // Remote Keys
    override suspend fun getDiscoverMoviesRemoteKeys(id: Int): MoviesRemoteKeys {
        return appDatabase.discoverMoviesRemoteKeysDao().getRemoteKeys(id)
    }

    override suspend fun insertAllDiscoverMoviesRemoteKeys(remoteKeys: List<MoviesRemoteKeys>) {
        appDatabase.discoverMoviesRemoteKeysDao().insertAllRemoteKeys(remoteKeys)
    }

    override suspend fun deleteAllDiscoverMoviesRemoteKeys() {
        appDatabase.discoverMoviesRemoteKeysDao().deleteAllRemoteKeys()
    }

    override suspend fun getDiscoverTvShowsRemoteKeys(id: Int): TvShowsRemoteKeys {
        return appDatabase.discoverTvShowsRemoteKeysDao().getRemoteKeys(id)
    }

    override suspend fun insertAllDiscoverTvShowsRemoteKeys(remoteKeys: List<TvShowsRemoteKeys>) {
        appDatabase.discoverTvShowsRemoteKeysDao().insertAllRemoteKeys(remoteKeys)
    }

    override suspend fun deleteAllDiscoverTvShowsRemoteKeys() {
        appDatabase.discoverTvShowsRemoteKeysDao().deleteAllRemoteKeys()
    }

    // Genres
    override suspend fun getAllMovieGenres(): Flow<List<GenresMovieModel>> {
        return appDatabase.genresMovieDao().getAllGenres()
    }

    override suspend fun insertMovieGenres(genres: List<GenresMovieModel>) {
        appDatabase.genresMovieDao().insertGenres(genres)
    }

    override suspend fun deleteAllMovieGenres() {
        appDatabase.genresMovieDao().deleteAllGenres()
    }

    override suspend fun getAllTvShowGenres(): Flow<List<GenresTvShowModel>> {
        return appDatabase.genresTvShowDao().getAllGenres()
    }

    override suspend fun insertTvShowGenres(genres: List<GenresTvShowModel>) {
        appDatabase.genresTvShowDao().insertGenres(genres)
    }

    override suspend fun deleteAllTvShowGenres() {
        appDatabase.genresTvShowDao().deleteAllGenres()
    }

    // ViewList Movies
    override fun getMovieViewListById(movieId: Int): Flow<List<MoviesViewList>> {
        return appDatabase.viewListMoviesDao().getMovieById(movieId)
    }

    override fun getAllMoviesViewList(): Flow<List<MoviesViewList>> {
        return appDatabase.viewListMoviesDao().getAllMovies()
    }

    override suspend fun insertMovieViewList(movieViewList: MoviesViewList) {
        appDatabase.viewListMoviesDao().insertMovie(movieViewList)
    }

    override suspend fun insertMoviesViewList(movieViewList: List<MoviesViewList>) {
        appDatabase.viewListMoviesDao().insertMovies(movieViewList)
    }

    override suspend fun updateMovieViewList(movieViewList: MoviesViewList) {
        appDatabase.viewListMoviesDao().updateMovie(movieViewList)
    }

    override suspend fun deleteAllMovieViewList() {
        appDatabase.viewListMoviesDao().deleteAllMovies()
    }

    // ViewList TV Shows
    override fun getTvShowViewListById(tvShowId: Int): Flow<List<TVShowsViewList>> {
        return appDatabase.viewListTvShowsDao().getTvShowById(tvShowId)
    }

    override fun getAllTvShowsViewList(): Flow<List<TVShowsViewList>> {
        return appDatabase.viewListTvShowsDao().getAllTvShows()
    }

    override suspend fun insertTvShowViewList(tvShowViewList: TVShowsViewList) {
        appDatabase.viewListTvShowsDao().insertTvShow(tvShowViewList)
    }

    override suspend fun insertTvShowsViewList(tvShowViewList: List<TVShowsViewList>) {
        appDatabase.viewListTvShowsDao().insertTvShows(tvShowViewList)
    }

    override suspend fun updateTvShowViewList(tvShowViewList: TVShowsViewList) {
        appDatabase.viewListTvShowsDao().updateTvShow(tvShowViewList)
    }

    override suspend fun deleteAllTvShowViewList() {
        appDatabase.viewListTvShowsDao().deleteAllTvShows()
    }

    // Pins
    override fun getAllPinsViewList(): Flow<List<PinViewListModel>> {
        return appDatabase.pinViewListDao().getAllPins()
    }

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
