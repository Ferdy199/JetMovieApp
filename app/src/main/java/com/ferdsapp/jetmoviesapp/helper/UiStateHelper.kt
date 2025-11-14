package com.ferdsapp.jetmoviesapp.helper

import com.ferdsapp.jetmoviesapp.data.utils.ApiResponse
import com.ferdsapp.jetmoviesapp.ui.screen.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlin.collections.emptyList

object UiStateHelper {
    fun <T> Flow<ApiResponse<List<T>>>.asUiStateList(): Flow<UiState<List<T>>> =
        map { res ->
            when (res) {
                is ApiResponse.Loading -> UiState.Loading
                is ApiResponse.Success -> UiState.Success(res.data)
                is ApiResponse.Empty   -> UiState.Success(emptyList())
                is ApiResponse.Error   -> UiState.Error(res.errorMessage)
            }
        }.onStart { emit(UiState.Loading) }

    fun <T> Flow<ApiResponse<T>>.asUiState(): Flow<UiState<T>> =
        map { res ->
            when (res) {
                is ApiResponse.Loading -> UiState.Loading
                is ApiResponse.Success -> UiState.Success(res.data)
                is ApiResponse.Empty   -> UiState.Error(res.toString())
                is ApiResponse.Error   -> UiState.Error(res.errorMessage)
            }
        }.onStart { emit(UiState.Loading) }
}