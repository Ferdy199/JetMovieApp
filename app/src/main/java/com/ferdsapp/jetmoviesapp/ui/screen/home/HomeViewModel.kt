package com.ferdsapp.jetmoviesapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferdsapp.jetmoviesapp.data.detail.movie.MovieDetailResponse
import com.ferdsapp.jetmoviesapp.helper.UiStateHelper.asUiState
import com.ferdsapp.jetmoviesapp.helper.UiStateHelper.asUiStateList
import com.ferdsapp.jetmoviesapp.repository.IMovieRepository
import com.ferdsapp.jetmoviesapp.ui.screen.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: IMovieRepository): ViewModel() {

    private val _movieDetailState = MutableStateFlow<UiState<MovieDetailResponse>>(UiState.Empty)
    val movieDetailState = _movieDetailState

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

    val upComingState = repository.getUpcomingMovie()
        .asUiState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading
        )


    fun movieDetail(movieId: Int)  {
        viewModelScope.launch {
            repository.getMovieDetail(movieId)
                .asUiState()
                .collect { state ->
                    _movieDetailState.value = state
                }
        }
    }

    fun clearMovieDetail() {
        _movieDetailState.value = UiState.Empty
    }
}