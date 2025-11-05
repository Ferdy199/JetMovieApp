package com.ferdsapp.jetmoviesapp.repository

import com.ferdsapp.jetmoviesapp.data.movie.ResultItem
import com.ferdsapp.jetmoviesapp.data.utils.ApiResponse
import com.ferdsapp.jetmoviesapp.source.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JetMovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): IMovieRepository {
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
        }
    }

}