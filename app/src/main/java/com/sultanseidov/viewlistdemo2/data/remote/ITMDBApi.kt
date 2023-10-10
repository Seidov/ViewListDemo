package com.sultanseidov.viewlistdemo2.data.remote

import com.sultanseidov.viewlistdemo2.data.model.dto.genre.ResponseMovieGenresListModel
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.ResponseTVShowGenresListModel
import com.sultanseidov.viewlistdemo2.data.model.dto.movie.MoviesDto
import com.sultanseidov.viewlistdemo2.data.model.dto.tvshow.TVShowDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ITMDBApi {

    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
        @Query("sort_by") sort_by: String = "popularity.desc",
        @Query("with_genres") with_genres: String
    ): Response<MoviesDto>

    @GET("discover/tv")
    suspend fun getDiscoverTvShows(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
        @Query("sort_by") sort_by: String = "popularity.desc",
        @Query("with_genres") with_genres: String
    ): Response<TVShowDto>

    @GET("genre/movie/list")
    suspend fun getMovieGenresList(
        @Query("api_key") api_key: String
    ): ResponseMovieGenresListModel

    @GET("genre/movie/list")
    suspend fun getTVShowGenresList(
        @Query("api_key") api_key: String
    ): ResponseTVShowGenresListModel

}