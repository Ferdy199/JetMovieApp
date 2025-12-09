package com.ferdsapp.jetmoviesapp.data.utils

sealed class MovieEntity <out T> {
    data class Success<out T>(val data: T): MovieEntity<T>()
    data class Error<T>(val errorMessage: String, val data: T? = null): MovieEntity<T>()
    data object Loading: MovieEntity<Nothing>()
    data object Empty: MovieEntity<Nothing>()
}