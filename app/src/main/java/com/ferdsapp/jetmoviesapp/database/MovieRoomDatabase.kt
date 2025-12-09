package com.ferdsapp.jetmoviesapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ferdsapp.jetmoviesapp.data.favorite.FavoriteMovie

@Database(entities = [FavoriteMovie::class], version = 1)
abstract class MovieRoomDatabase: RoomDatabase() {
    abstract fun favoriteMovieDao(): MovieDao
}