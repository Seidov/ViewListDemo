package com.sultanseidov.viewlistdemo2.data.remote

import com.sultanseidov.viewlistdemo2.data.entity.GenreModel
import com.sultanseidov.viewlistdemo2.data.entity.ResponseGenresListModel
import com.sultanseidov.viewlistdemo2.data.entity.ResponsePopularMoviesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ITmdbApi {
    @GET("discover/movie")
    suspend fun getPopularMovies(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
        @Query("sort_by") sort_by: String = "popularity.desc",
        @Query("with_genres") with_genres: String
    ): Response<ResponsePopularMoviesModel>

    @GET("genre/movie/list")
    suspend fun getMovieGenresList(
        @Query("api_key") api_key: String
    ): ResponseGenresListModel

}