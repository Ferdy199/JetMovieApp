package com.ferdsapp.jetmoviesapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferdsapp.jetmoviesapp.data.favorite.FavoriteMovie
import com.ferdsapp.jetmoviesapp.repository.IMovieRepository
import com.ferdsapp.jetmoviesapp.ui.screen.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: IMovieRepository): ViewModel() {
    private val _uiState : MutableStateFlow<UiState<Boolean>> = MutableStateFlow(UiState.Loading)
    val uiState : StateFlow<UiState<Boolean>>
        get() = _uiState

    fun addFavoriteMovie(favoriteMovie: FavoriteMovie) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
          repository.addMovieFavorite(favoriteMovie)
            _uiState.value = UiState.Success(true)
        }
    }

}