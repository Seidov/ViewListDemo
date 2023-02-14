package com.sultanseidov.viewlistdemo2.data.remote

import com.sultanseidov.viewlistdemo2.data.entity.ResponsePopularMoviesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ITmdbApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Response<ResponsePopularMoviesModel>

}