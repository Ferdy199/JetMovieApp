package com.ferdsapp.jetmoviesapp.di

import com.ferdsapp.jetmoviesapp.repository.IMovieRepository
import com.ferdsapp.jetmoviesapp.repository.JetMovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(jetMovieRepository: JetMovieRepository): IMovieRepository
}