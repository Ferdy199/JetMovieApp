package com.ferdsapp.jetmoviesapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferdsapp.jetmoviesapp.data.ResultItem
import com.ferdsapp.jetmoviesapp.data.utils.ApiResponse
import com.ferdsapp.jetmoviesapp.repository.IMovieRepository
import com.ferdsapp.jetmoviesapp.ui.screen.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: IMovieRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<ResultItem>>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<ResultItem>>>
        get() = _uiState

    fun getMovieNowPlaying() {
        viewModelScope.launch {
            repository.getNowMoviePlaying()
                .collect { movieNowPlayingResponses ->
                    when(movieNowPlayingResponses){
                        is ApiResponse.Empty -> {}
                        is ApiResponse.Error -> {
                            _uiState.value = UiState.Error(movieNowPlayingResponses.errorMessage)
                        }
                        is ApiResponse.Loading -> {
                            _uiState.value = UiState.Loading
                        }
                        is ApiResponse.Success -> {
                            _uiState.value = UiState.Success(movieNowPlayingResponses.data)
                        }
                    }

                }
        }
    }
}