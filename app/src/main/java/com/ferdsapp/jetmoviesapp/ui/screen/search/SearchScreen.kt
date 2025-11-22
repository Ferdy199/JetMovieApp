package com.ferdsapp.jetmoviesapp.ui.screen.search

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices.PIXEL_3
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ferdsapp.jetmoviesapp.data.search.SearchResponses
import com.ferdsapp.jetmoviesapp.ui.screen.components.EmptyDialog
import com.ferdsapp.jetmoviesapp.ui.screen.components.ErrorDialog
import com.ferdsapp.jetmoviesapp.ui.screen.components.LoadingDialog
import com.ferdsapp.jetmoviesapp.ui.screen.components.SearchBarApp
import com.ferdsapp.jetmoviesapp.ui.screen.state.UiState
import com.ferdsapp.jetmoviesapp.ui.theme.JetMoviesAppTheme

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier,
    ) {
        val query by viewModel.query.collectAsStateWithLifecycle()
        val state by viewModel.searchState2.collectAsStateWithLifecycle()

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
            is UiState.Empty -> EmptyDialog()
            is UiState.Error -> ErrorDialog()
            is UiState.Loading -> LoadingDialog()
            is UiState.Success-> {
                val searchResponses = (state as UiState.Success<SearchResponses>).data
                Log.d("SearchResult", "SearchScreen: ${searchResponses.results}")
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Success get data"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = PIXEL_3)
@Composable
private fun SearchScreenPreview() {
    JetMoviesAppTheme {
        SearchScreen()
    }
}