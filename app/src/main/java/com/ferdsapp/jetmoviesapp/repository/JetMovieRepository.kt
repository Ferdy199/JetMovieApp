package com.ferdsapp.jetmoviesapp.repository

import com.ferdsapp.jetmoviesapp.data.detail.movie.MovieDetailResponse
import com.ferdsapp.jetmoviesapp.data.favorite.FavoriteMovie
import com.ferdsapp.jetmoviesapp.data.movie.ResultItem
import com.ferdsapp.jetmoviesapp.data.search.SearchResponses
import com.ferdsapp.jetmoviesapp.data.tv.TvResultItem
import com.ferdsapp.jetmoviesapp.data.upcoming.UpcomingResponses
import com.ferdsapp.jetmoviesapp.data.utils.ApiResponse
import com.ferdsapp.jetmoviesapp.data.utils.MovieEntity
import com.ferdsapp.jetmoviesapp.source.LocalDataSource
import com.ferdsapp.jetmoviesapp.source.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JetMovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): IMovieRepository {

    private val movieFavorite = mutableListOf<FavoriteMovie>()

    override fun getNowMoviePlaying(): Flow<ApiResponse<List<ResultItem>>> {
        return flow {
            try {
                remoteDataSource.getNowPlayingMovie().collect { nowPlayingResponses ->
                    when(nowPlayingResponses){
                        is ApiResponse.Empty -> emit(ApiResponse.Empty)
                        is ApiResponse.Error -> emit(ApiResponse.Error(nowPlayingResponses.errorMessage))
                        is ApiResponse.Loading -> emit(ApiResponse.Loading)
                        is ApiResponse.Success -> {
                            val movieList = nowPlayingResponses.data
                            emit(ApiResponse.Success(movieList))
                        }
                    }
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getTvAiringToday(): Flow<ApiResponse<List<TvResultItem>>> {
        return flow {
            try {
                remoteDataSource.getTvAiringToday().collect { getTvAiringResponses ->
                    when(getTvAiringResponses){
                        is ApiResponse.Empty -> emit(ApiResponse.Empty)
                        is ApiResponse.Error -> emit(ApiResponse.Error(getTvAiringResponses.errorMessage))
                        is ApiResponse.Loading -> emit(ApiResponse.Loading)
                        is ApiResponse.Success -> {
                            emit(ApiResponse.Success(getTvAiringResponses.data))
                        }
                    }
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(errorMessage = e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getUpcomingMovie(): Flow<ApiResponse<UpcomingResponses>> {
        return flow {
            try {
                remoteDataSource.getUpcomingMovie().collect { upComingResponses ->
                    when(upComingResponses){
                        is ApiResponse.Empty -> emit(ApiResponse.Empty)
                        is ApiResponse.Error -> emit(ApiResponse.Error(upComingResponses.errorMessage))
                        is ApiResponse.Loading -> emit(ApiResponse.Loading)
                        is ApiResponse.Success -> {
                            emit(ApiResponse.Success(upComingResponses.data))
                        }
                    }
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getSearchResponses(query: String): Flow<ApiResponse<SearchResponses>> {
        return flow {
            try {
                remoteDataSource.getSearchFeatures(query = query).collect { searchResponses ->
                    when(searchResponses){
                        is ApiResponse.Empty -> emit(ApiResponse.Empty)
                        is ApiResponse.Error -> emit(ApiResponse.Error(searchResponses.errorMessage))
                        is ApiResponse.Loading -> emit(ApiResponse.Loading)
                        is ApiResponse.Success -> emit(ApiResponse.Success(searchResponses.data))
                    }
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getMovieDetail(media_type: String, movieId: Int): Flow<ApiResponse<MovieDetailResponse>> {
        return flow {
            try {
                remoteDataSource.movieDetail(media_type,movieId).collect { detailResponse ->
                    when(detailResponse){
                        is ApiResponse.Empty -> emit(ApiResponse.Empty)
                        is ApiResponse.Error -> emit(ApiResponse.Error(detailResponse.errorMessage))
                        is ApiResponse.Loading -> emit(ApiResponse.Loading)
                        is ApiResponse.Success -> {
                            emit(ApiResponse.Success(detailResponse.data))
                        }
                    }
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getAllFavorite(): Flow<MovieEntity<List<FavoriteMovie>>>{
        return flow {
            try {
                localDataSource.getAllFavoriteMovie().collect { favoriteMovie ->
                    when(favoriteMovie){
                        is MovieEntity.Empty -> emit(MovieEntity.Empty)
                        is MovieEntity.Error -> emit(MovieEntity.Error(errorMessage = favoriteMovie.errorMessage))
                        is MovieEntity.Loading -> emit(MovieEntity.Loading)
                        is MovieEntity.Success-> {
                            emit(MovieEntity.Success(favoriteMovie.data))
                        }
                    }
                }
            }catch (e: Exception){
                emit(MovieEntity.Error(errorMessage = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun addMovieFavorite(favoriteMovie: FavoriteMovie) {
       localDataSource.insertFavorite(favoriteMovie = favoriteMovie)
    }

    override suspend fun updateMovieFavorite(favoriteMovie: FavoriteMovie) {
        localDataSource.updateFavorite(favoriteMovie)
    }

    override suspend fun deleteMovieFavorite(favoriteMovie: FavoriteMovie) {
        localDataSource.deleteFavorite(favoriteMovie)
    }

}