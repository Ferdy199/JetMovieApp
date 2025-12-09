package com.ferdsapp.jetmoviesapp.di

import android.content.Context
import androidx.room.Room
import com.ferdsapp.jetmoviesapp.database.MovieDao
import com.ferdsapp.jetmoviesapp.database.MovieRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(
        context: Context
    ): MovieRoomDatabase{
        return Room.databaseBuilder(
            context = context,
            MovieRoomDatabase::class.java,
            "movie_database"
        ).build()
    }

    @Provides
    fun provideMovieDao(db: MovieRoomDatabase): MovieDao = db.favoriteMovieDao()
}