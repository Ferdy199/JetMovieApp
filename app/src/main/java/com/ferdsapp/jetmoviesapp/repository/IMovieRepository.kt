package com.ferdsapp.jetmoviesapp.repository

import com.ferdsapp.jetmoviesapp.data.ResultItem
import com.ferdsapp.jetmoviesapp.data.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getNowMoviePlaying(): Flow<ApiResponse<List<ResultItem>>>
}