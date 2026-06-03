package com.sultanseidov.viewlistdemo2.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sultanseidov.viewlistdemo2.BuildConfig.API_KEY
import com.sultanseidov.viewlistdemo2.data.model.mappers.movie.toMovieList
import com.sultanseidov.viewlistdemo2.data.remote.api.ITMDBApi
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel

class SearchMoviesPagingSource(
    private val api: ITMDBApi,
    private val query: String
) : PagingSource<Int, MovieModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        val currentPage = params.key ?: 1
        return try {
            val response = api.searchMovies(api_key = API_KEY, query = query, page = currentPage)
            if (response.isSuccessful) {
                val movies = response.body()?.toMovieList() ?: emptyList()
                LoadResult.Page(
                    data = movies,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (movies.isEmpty()) null else currentPage + 1
                )
            } else {
                LoadResult.Error(Exception("Failed to load search results"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return state.anchorPosition
    }
}
