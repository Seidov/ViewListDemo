package com.sultanseidov.viewlistdemo2.domain.usecase.search

import androidx.paging.PagingData
import androidx.paging.filter
import com.sultanseidov.viewlistdemo2.data.local.dao.WatchedMovieDao
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.domain.repository.IMovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFilteredSearchResultsUseCase @Inject constructor(
    private val repository: IMovieRepository,
    private val watchedMovieDao: WatchedMovieDao
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(query: String): Flow<PagingData<MovieModel>> {
        return watchedMovieDao.getAllWatchedMovieIdsFlow().flatMapLatest { watchedIds ->
            repository.searchMovies(query).map { pagingData ->
                pagingData.filter { movie ->
                    !watchedIds.contains(movie.id.toLong())
                }
            }
        }
    }
}
