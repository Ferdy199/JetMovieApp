package com.ferdsapp.jetmoviesapp.repository

import com.ferdsapp.jetmoviesapp.data.movie.ResultItem
import com.ferdsapp.jetmoviesapp.data.tv.TvResultItem
import com.ferdsapp.jetmoviesapp.data.upcoming.UpcomingResponses
import com.ferdsapp.jetmoviesapp.data.upcoming.UpcomingResults
import com.ferdsapp.jetmoviesapp.data.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getNowMoviePlaying(): Flow<ApiResponse<List<ResultItem>>>
    fun getTvAiringToday(): Flow<ApiResponse<List<TvResultItem>>>
    fun getUpcomingMovie(): Flow<ApiResponse<UpcomingResponses>>
}