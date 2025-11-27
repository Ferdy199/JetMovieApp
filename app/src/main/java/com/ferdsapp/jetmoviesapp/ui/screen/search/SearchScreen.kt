package com.ferdsapp.jetmoviesapp.ui.screen.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices.PIXEL_3
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ferdsapp.jetmoviesapp.data.detail.movie.MovieDetailResponse
import com.ferdsapp.jetmoviesapp.data.search.SearchResponses
import com.ferdsapp.jetmoviesapp.ui.screen.components.EmptyDialog
import com.ferdsapp.jetmoviesapp.ui.screen.components.ErrorDialog
import com.ferdsapp.jetmoviesapp.ui.screen.components.LoadingDialog
import com.ferdsapp.jetmoviesapp.ui.screen.components.MovieItem
import com.ferdsapp.jetmoviesapp.ui.screen.components.SearchBarApp
import com.ferdsapp.jetmoviesapp.ui.screen.state.UiState
import com.ferdsapp.jetmoviesapp.ui.theme.JetMoviesAppTheme

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    navigateToDetail: (Int, String, String, String, String) -> Unit
) {
    Column(
        modifier = modifier,
    ) {
        val query by viewModel.query.collectAsStateWithLifecycle()
        val state by viewModel.searchState.collectAsStateWithLifecycle()
        val movieDetailState by viewModel.movieDetailState.collectAsStateWithLifecycle()

        SearchBarApp(
            query = query,
            onQueryChange = {
                viewModel.onQueryChanged(it)
            },
            onSearch = {
                viewModel.searchNow(it)
            }
        )

        when(state){
            is UiState.Empty -> {
                EmptyDialog()
            }
            is UiState.Error -> {
                ErrorDialog()
            }
            is UiState.Loading -> LoadingDialog()
            is UiState.Success-> {
                val searchResponses = (state as UiState.Success<SearchResponses>).data
                Log.d("SearchResult", "SearchScreen: ${searchResponses.results}")

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier
                ) {
                    items(searchResponses.results, key = {it.id}) { searchResponses ->
                        MovieItem(
                            backdrop_path = searchResponses.poster_path,
                            title = searchResponses.original_title ?: searchResponses.name ?: searchResponses.original_name ?: "-",
                            modifier = Modifier.clickable{
                                viewModel.movieDetail(searchResponses.media_type, searchResponses.id)
                            }
                        )
                    }
                }
            }
        }

        LaunchedEffect(movieDetailState) {
            if (movieDetailState is UiState.Success){
                val detailData = (movieDetailState as UiState.Success<MovieDetailResponse>).data
                viewModel.clearMovieDetail()
                navigateToDetail(
                    detailData.id,
                    detailData.original_title ?: "",
                    detailData.overview ?: "",
                    detailData.poster_path ?: "",
                    detailData.backdrop_path ?: ""
                )
            }
        }
    }
}