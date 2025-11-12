package com.ferdsapp.jetmoviesapp.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ferdsapp.jetmoviesapp.data.movie.ResultItem
import com.ferdsapp.jetmoviesapp.data.tv.TvResultItem
import com.ferdsapp.jetmoviesapp.ui.screen.components.MovieItem
import com.ferdsapp.jetmoviesapp.ui.screen.components.SectionText
import com.ferdsapp.jetmoviesapp.ui.screen.components.TvItem
import com.ferdsapp.jetmoviesapp.ui.screen.state.UiState
import com.ferdsapp.jetmoviesapp.ui.theme.JetMoviesAppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
    ) {
    Column(
        modifier = Modifier
    ) {
        NowPlayingSection(viewModel = viewModel ,modifier = modifier)
        NowAiringSection(viewModel = viewModel, modifier = modifier)
    }

}

@Preview
@Composable
private fun HomeScreenPreview() {
    JetMoviesAppTheme {
        HomeScreen()
    }
}

@Composable
fun NowPlayingSection(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.movieUiState.collectAsStateWithLifecycle()

    when(state){
        is UiState.Error -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Failed get data"
                )
            }
        }
        UiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Loading get data"
                )
            }
        }
        is UiState.Success -> {
            val data = (state as UiState.Success<List<ResultItem>>).data
            SectionText("In Theaters")
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(data, key =  {it.id}) { movie ->
                    MovieItem(movieItem = movie)
                }
            }
        }
    }
}

@Composable
fun NowAiringSection(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.tvUiState.collectAsStateWithLifecycle()
    when(state){
        is UiState.Error -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Failed get data"
                )
            }
        }
        is UiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Loading get data"
                )
            }
        }
        is UiState.Success -> {
            val data = (state as UiState.Success<List<TvResultItem>>).data
            SectionText("On The Air")
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(data, key =  {it.id}) { tv ->
                    TvItem(tvItem = tv)
                }
            }
        }
    }
}