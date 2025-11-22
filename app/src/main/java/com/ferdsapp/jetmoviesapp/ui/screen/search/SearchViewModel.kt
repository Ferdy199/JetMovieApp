package com.ferdsapp.jetmoviesapp.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferdsapp.jetmoviesapp.data.search.SearchResponses
import com.ferdsapp.jetmoviesapp.helper.UiStateHelper.asUiState
import com.ferdsapp.jetmoviesapp.repository.IMovieRepository
import com.ferdsapp.jetmoviesapp.ui.screen.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(val repository: IMovieRepository): ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    fun searchState1(): StateFlow<UiState<SearchResponses>>{
        return _query
            .debounce(300)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                if (query.isBlank() || query.isEmpty()){
                    flow {
                        emit(UiState.Empty)
                    }
                }else{
                    repository.getSearchResponses(query).asUiState()
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UiState.Empty
            )
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val searchState2: StateFlow<UiState<SearchResponses>> = _query
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flow {
                    emit(UiState.Empty)
                }
            } else {
                repository.getSearchResponses(query).asUiState()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Empty
        )


    // dipanggil oleh UI saat user ketik
    fun onQueryChanged(query: String) {
        _query.value = query
    }

    // dipanggil saat user tekan search; set query supaya flatMapLatest langsung jalan
    fun searchNow(query: String) {
        _query.value = query
    }
}