package com.sultanseidov.viewlistdemo2.data.remote

import com.sultanseidov.viewlistdemo2.data.entity.genre.ResponseGenresListModel
import com.sultanseidov.viewlistdemo2.data.entity.movie.ResponseDiscoverMoviesModel
import com.sultanseidov.viewlistdemo2.data.entity.tvshow.ResponseDiscoverTvShowsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ITmdbApi {
    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
        @Query("sort_by") sort_by: String = "popularity.desc",
        @Query("with_genres") with_genres: String
    ): Response<ResponseDiscoverMoviesModel>

    @GET("discover/tv")
    suspend fun getDiscoverTvShows(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
        @Query("sort_by") sort_by: String = "popularity.desc",
        @Query("with_genres") with_genres: String
    ): Response<ResponseDiscoverTvShowsModel>

    @GET("genre/movie/list")
    suspend fun getMovieGenresList(
        @Query("api_key") api_key: String
    ): ResponseGenresListModel

}