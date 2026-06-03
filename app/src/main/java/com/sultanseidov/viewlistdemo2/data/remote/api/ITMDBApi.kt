package com.sultanseidov.viewlistdemo2.data.remote.api

import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresDto
import com.sultanseidov.viewlistdemo2.data.model.dto.movie.KeywordResponse
import com.sultanseidov.viewlistdemo2.data.model.dto.movie.MoviesDto
import com.sultanseidov.viewlistdemo2.data.model.dto.movie.Result
import com.sultanseidov.viewlistdemo2.data.model.dto.tvshow.TVShowDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
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
    ): GenresDto

    @GET("genre/tv/list")
    suspend fun getTVShowGenresList(
        @Query("api_key") api_key: String
    ): GenresDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movie_id: Long,
        @Query("api_key") api_key: String
    ): Result

    @GET("movie/{movie_id}/keywords")
    suspend fun getMovieKeywords(
        @Path("movie_id") movie_id: Long,
        @Query("api_key") api_key: String
    ): KeywordResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") api_key: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<MoviesDto>

}
