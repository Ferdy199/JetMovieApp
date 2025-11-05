package com.ferdsapp.jetmoviesapp.source

import com.ferdsapp.jetmoviesapp.data.movie.ResultItem
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
                val token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiYjVkN2YwZjc5ZGE0MGFmYjc2NDkzZmJjZTIyNzg1ZSIsIm5iZiI6MTczMDU1NTIwNC41NDI5MzMyLCJzdWIiOiI2MGM5YTM3ODJmY2NlZTAwMjhhMTgzMWUiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.wcg8J1BRGikoAzUq_Q6wYgxKuPTgXw0MgJOSvuBdt94"
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
}