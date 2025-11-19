package com.ferdsapp.jetmoviesapp.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferdsapp.jetmoviesapp.helper.UiStateHelper.asUiState
import com.ferdsapp.jetmoviesapp.helper.UiStateHelper.asUiStateList
import com.ferdsapp.jetmoviesapp.repository.IMovieRepository
import com.ferdsapp.jetmoviesapp.ui.screen.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(val repository: IMovieRepository): ViewModel() {
    fun searchState(query: String) = repository.getSearchResponses(query)
        .asUiState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading
        )
}