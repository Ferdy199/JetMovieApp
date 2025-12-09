package com.ferdsapp.jetmoviesapp.repository

import com.ferdsapp.jetmoviesapp.data.detail.movie.MovieDetailResponse
import com.ferdsapp.jetmoviesapp.data.favorite.FavoriteMovie
import com.ferdsapp.jetmoviesapp.data.movie.ResultItem
import com.ferdsapp.jetmoviesapp.data.search.SearchResponses
import com.ferdsapp.jetmoviesapp.data.tv.TvResultItem
import com.ferdsapp.jetmoviesapp.data.upcoming.UpcomingResponses
import com.ferdsapp.jetmoviesapp.data.utils.ApiResponse
import com.ferdsapp.jetmoviesapp.data.utils.MovieEntity
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getNowMoviePlaying(): Flow<ApiResponse<List<ResultItem>>>
    fun getTvAiringToday(): Flow<ApiResponse<List<TvResultItem>>>
    fun getUpcomingMovie(): Flow<ApiResponse<UpcomingResponses>>
    fun getSearchResponses(query: String): Flow<ApiResponse<SearchResponses>>
    fun getMovieDetail(media_type: String, movieId: Int): Flow<ApiResponse<MovieDetailResponse>>
    fun getAllFavorite(): Flow<MovieEntity<List<FavoriteMovie>>>
    suspend fun addMovieFavorite(favoriteMovie: FavoriteMovie)
    suspend fun updateMovieFavorite(favoriteMovie: FavoriteMovie)
    suspend fun deleteMovieFavorite(favoriteMovie: FavoriteMovie)
}