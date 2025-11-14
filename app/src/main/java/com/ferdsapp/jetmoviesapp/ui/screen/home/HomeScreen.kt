package com.ferdsapp.jetmoviesapp.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ferdsapp.jetmoviesapp.data.movie.ResultItem
import com.ferdsapp.jetmoviesapp.data.tv.TvResultItem
import com.ferdsapp.jetmoviesapp.data.upcoming.UpcomingResponses
import com.ferdsapp.jetmoviesapp.ui.screen.components.ErrorDialog
import com.ferdsapp.jetmoviesapp.ui.screen.components.LoadingDialog
import com.ferdsapp.jetmoviesapp.ui.screen.components.MovieItem
import com.ferdsapp.jetmoviesapp.ui.screen.components.SectionText
import com.ferdsapp.jetmoviesapp.ui.screen.components.TvItem
import com.ferdsapp.jetmoviesapp.ui.screen.components.UpComingItem
import com.ferdsapp.jetmoviesapp.ui.screen.state.UiState
import com.ferdsapp.jetmoviesapp.ui.theme.JetMoviesAppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
    ) {


    val movieState by viewModel.movieUiState.collectAsStateWithLifecycle()
    val tvState by viewModel.tvUiState.collectAsStateWithLifecycle()
    val upcomingState by viewModel.upComingState.collectAsStateWithLifecycle()

    val isLoading = remember(movieState, tvState, upcomingState) {
        derivedStateOf {
            movieState == UiState.Loading || tvState == UiState.Loading || upcomingState == UiState.Loading
        }
    }

    val listState = rememberLazyListState()

    Box(
        modifier = Modifier
    ) {

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            item{
                NowPlayingSection(movieState, modifier = modifier)
            }

            item {
                NowAiringSection(state = tvState, modifier = modifier)
            }

            item {
                UpcomingMovieSection(state = upcomingState, modifier = modifier)
            }
        }

        if (isLoading.value){
            LoadingDialog()
        }

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
    state:  UiState<List<ResultItem>>,
    modifier: Modifier = Modifier
) {
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
        UiState.Loading -> {}
        is UiState.Success -> {
            val data = state.data
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
    state:  UiState<List<TvResultItem>>,
    modifier: Modifier = Modifier
) {
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
        is UiState.Loading -> {}
        is UiState.Success -> {
            val data = state.data
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

@Composable
fun UpcomingMovieSection(
    state:  UiState<UpcomingResponses>,
    modifier: Modifier = Modifier
) {
    when(state){
        is UiState.Error -> {
            ErrorDialog(
                modifier = modifier
            )
        }
        is UiState.Loading -> {}
        is UiState.Success -> {
            val responsesData = state.data
            SectionText("Upcoming")
            Column(
                verticalArrangement  = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxHeight().padding(start = 16.dp, end = 8.dp, bottom = 16.dp),
            ) {
                responsesData.results.forEachIndexed { index, upcoming ->
                    UpComingItem(
                        releasedDate = responsesData.dates,
                        upcomingResults = upcoming,
                        order = (index + 1).toString()
                    )
                    // spacing antar item
                    if (index < responsesData.results.lastIndex) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}