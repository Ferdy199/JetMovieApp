package com.ferdsapp.jetmoviesapp.source

import com.ferdsapp.jetmoviesapp.data.favorite.FavoriteMovie
import com.ferdsapp.jetmoviesapp.data.utils.MovieEntity
import com.ferdsapp.jetmoviesapp.database.MovieDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao
) {
    fun getAllFavoriteMovie() : Flow<MovieEntity<List<FavoriteMovie>>> {
        return flow {
            emit(MovieEntity.Loading)
            try {
                val getFavoriteMovie = movieDao.getAllFavoriteMovie()
                emit(MovieEntity.Success(getFavoriteMovie))
            }catch (e: Exception){
                emit(MovieEntity.Error(errorMessage = e.message.toString()))
            }
        }
    }

    suspend fun insertFavorite(favoriteMovie: FavoriteMovie) = movieDao.insert(favoriteMovie)

    suspend fun updateFavorite(favoriteMovie: FavoriteMovie) = movieDao.update(favoriteMovie)

    suspend fun deleteFavorite(favoriteMovie: FavoriteMovie) = movieDao.delete(favoriteMovie)
}