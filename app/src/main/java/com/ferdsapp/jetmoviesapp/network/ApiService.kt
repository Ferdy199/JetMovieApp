package com.ferdsapp.jetmoviesapp.network

import com.ferdsapp.jetmoviesapp.data.detail.movie.MovieDetailResponse
import com.ferdsapp.jetmoviesapp.data.movie.MovieNowPlayingResponses
import com.ferdsapp.jetmoviesapp.data.search.SearchResponses
import com.ferdsapp.jetmoviesapp.data.search.SearchResponsesItem
import com.ferdsapp.jetmoviesapp.data.tv.TvAiringResponse
import com.ferdsapp.jetmoviesapp.data.upcoming.UpcomingResponses
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("3/movie/now_playing")
    suspend fun getNowPlayingMovie(
        @Header("Authorization")
        authToken: String,

        @Query("language") language: String = "id-ID",

        @Query("page") page: Int = 1,

        @Query("region") region: String = "id"
    ): MovieNowPlayingResponses

    @GET("/3/tv/airing_today")
    suspend fun getTvAiringToday(
        @Header("Authorization")
        authToken: String,

        @Query("language") language: String = "id-ID",

        @Query("page") page: Int = 1,

        @Query("region") region: String = "id"
    ): TvAiringResponse

    @GET("3/movie/upcoming")
    suspend fun getUpcomingMovie(
        @Header("Authorization")
        authToken: String,

        @Query("language") language: String = "id-ID",

        @Query("page") page: Int = 1,

        @Query("region") region: String = "id"
    ): UpcomingResponses

    @GET("3/search/multi")
    suspend fun getSearchFeatures(
        @Header("Authorization")
        authToken: String,

        @Query("query")
        query: String,

        @Query("language") language: String = "id-ID",

        @Query("page") page: Int = 1,

        @Query("region") region: String = "id"
    ): SearchResponses

    @GET("3/{media_type}/{id}")
    suspend fun getMovieDetail(
        @Header("Authorization")
        authToken: String,

        @Path("media_type")
        mediaType: String,

        @Path("id")
        id: Int,

        @Query("language")
        language: String = "en-US"
    ): MovieDetailResponse
}