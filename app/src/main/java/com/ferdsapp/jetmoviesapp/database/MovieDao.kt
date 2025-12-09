package com.ferdsapp.jetmoviesapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ferdsapp.jetmoviesapp.data.favorite.FavoriteMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteMovie: FavoriteMovie)

    @Update
    fun update(favoriteMovie: FavoriteMovie)

    @Delete
    fun delete(favoriteMovie: FavoriteMovie)

    @Query("Select * From favoriteMovie ORDER BY id ASC")
    fun getAllFavoriteMovie(): List<FavoriteMovie>
}