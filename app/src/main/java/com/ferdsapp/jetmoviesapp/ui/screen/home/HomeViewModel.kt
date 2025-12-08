package com.ferdsapp.jetmoviesapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferdsapp.jetmoviesapp.data.detail.movie.MovieDetailResponse
import com.ferdsapp.jetmoviesapp.helper.UiStateHelper.asUiState
import com.ferdsapp.jetmoviesapp.helper.UiStateHelper.asUiStateList
import com.ferdsapp.jetmoviesapp.repository.IMovieRepository
import com.ferdsapp.jetmoviesapp.ui.screen.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: IMovieRepository): ViewModel() {

    private val _movieDetailState = MutableStateFlow<UiState<MovieDetailResponse>>(UiState.Empty)
    val movieDetailState = _movieDetailState

    private val _refresh = MutableStateFlow(false)
    val refreshState = _refresh

    private val refreshTrigger = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val movieUiState = refreshTrigger
        .flatMapLatest {
            repository.getNowMoviePlaying()
                .asUiStateList()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val tvUiState = refreshTrigger.flatMapLatest {
        repository.getTvAiringToday()
            .asUiStateList()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UiState.Loading
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val upComingState = refreshTrigger.flatMapLatest {
        repository.getUpcomingMovie()
            .asUiState()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UiState.Loading
    )


    fun movieDetail(media_type: String,movieId: Int)  {
        viewModelScope.launch {
            repository.getMovieDetail(media_type,movieId)
                .asUiState()
                .collect { state ->
                    _movieDetailState.value = state
                }
        }
    }

    fun clearMovieDetail() {
        _movieDetailState.value = UiState.Empty
    }

    fun refreshScreen() {
        viewModelScope.launch {
            _refresh.value = true
            refreshTrigger.update { it + 1 }

            combine(movieUiState, tvUiState, upComingState) { a, b, c -> Triple(a, b, c) }
                .first { (a, b, c) ->
                    a !is UiState.Loading && b !is UiState.Loading && c !is UiState.Loading
                }

            _refresh.value = false
        }
    }
}