package com.ferdsapp.jetmoviesapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferdsapp.jetmoviesapp.helper.UiStateHelper.asUiStateList
import com.ferdsapp.jetmoviesapp.repository.IMovieRepository
import com.ferdsapp.jetmoviesapp.ui.screen.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: IMovieRepository): ViewModel() {

    val movieUiState = repository.getNowMoviePlaying()
        .asUiStateList()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading
        )

    val tvUiState = repository.getTvAiringToday()
        .asUiStateList()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading
        )
}