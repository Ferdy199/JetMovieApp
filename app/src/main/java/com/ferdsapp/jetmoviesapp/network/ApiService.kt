package com.ferdsapp.jetmoviesapp.network

import com.ferdsapp.jetmoviesapp.data.movie.MovieNowPlayingResponses
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("3/movie/now_playing")
    suspend fun getNowPlayingMovie(
        @Header("Authorization")
        authToken: String,

        @Query("language") language: String = "id-ID",

        @Query("page") page: Int = 1
    ): MovieNowPlayingResponses
}