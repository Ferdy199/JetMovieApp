package com.ferdsapp.jetmoviesapp.source

import com.ferdsapp.jetmoviesapp.BuildConfig
import com.ferdsapp.jetmoviesapp.data.movie.ResultItem
import com.ferdsapp.jetmoviesapp.data.tv.TvResultItem
import com.ferdsapp.jetmoviesapp.data.utils.ApiResponse
import com.ferdsapp.jetmoviesapp.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor (
   private val apiService: ApiService
) {
    fun getNowPlayingMovie(): Flow<ApiResponse<List<ResultItem>>>{
        return flow {
            emit(ApiResponse.Loading)
            try {
                val token = BuildConfig.API_TOKEN
                val responses = apiService.getNowPlayingMovie(
                    authToken = "Bearer $token"
                )
                val dataResponses = responses.results

                emit(ApiResponse.Success(dataResponses))

            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getTvAiringToday(): Flow<ApiResponse<List<TvResultItem>>>{
        return flow {
            emit(ApiResponse.Loading)
            try {
                val token = BuildConfig.API_TOKEN
                val responses = apiService.getTvAiringToday(
                    authToken = "Bearer $token"
                )
                val dataResponses = responses.results

                emit(ApiResponse.Success(dataResponses))

            }catch (e: Exception){
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}