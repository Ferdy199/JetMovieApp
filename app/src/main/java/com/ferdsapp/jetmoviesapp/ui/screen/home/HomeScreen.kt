package com.ferdsapp.jetmoviesapp.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ferdsapp.jetmoviesapp.data.detail.movie.MovieDetailGenre
import com.ferdsapp.jetmoviesapp.data.detail.movie.MovieDetailResponse
import com.ferdsapp.jetmoviesapp.data.movie.ResultItem
import com.ferdsapp.jetmoviesapp.data.tv.TvResultItem
import com.ferdsapp.jetmoviesapp.data.upcoming.UpcomingResponses
import com.ferdsapp.jetmoviesapp.ui.screen.components.EmptyDialog
import com.ferdsapp.jetmoviesapp.ui.screen.components.ErrorDialog
import com.ferdsapp.jetmoviesapp.ui.screen.components.LoadingDialog
import com.ferdsapp.jetmoviesapp.ui.screen.components.MovieItem
import com.ferdsapp.jetmoviesapp.ui.screen.components.PullToRefresh
import com.ferdsapp.jetmoviesapp.ui.screen.components.SectionText
import com.ferdsapp.jetmoviesapp.ui.screen.components.UpComingItem
import com.ferdsapp.jetmoviesapp.ui.screen.state.UiState
import com.ferdsapp.jetmoviesapp.ui.theme.JetMoviesAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (Int, String, String, List<MovieDetailGenre>, String, String) -> Unit
    ) {

    val refreshState by viewModel.refreshState.collectAsStateWithLifecycle()

    PullToRefresh(
        isRefreshing = refreshState,
        onRefresh = viewModel::refreshScreen,
        modifier = Modifier
    ) {
        val movieState by viewModel.movieUiState.collectAsStateWithLifecycle()
        val tvState by viewModel.tvUiState.collectAsStateWithLifecycle()
        val upcomingState by viewModel.upComingState.collectAsStateWithLifecycle()
        val movieDetailState by viewModel.movieDetailState.collectAsStateWithLifecycle()

        val isLoading by derivedStateOf {
            movieState is UiState.Loading ||
                    tvState is UiState.Loading ||
                    upcomingState is UiState.Loading ||
                    movieDetailState is UiState.Loading
        }

        val isEmpty by derivedStateOf {
            movieState is UiState.Empty &&
                    tvState is UiState.Empty &&
                    upcomingState is UiState.Empty &&
                    movieDetailState is UiState.Empty
        }

        val isError by derivedStateOf {
            movieState is UiState.Error ||
                    tvState is UiState.Error ||
                    upcomingState is UiState.Error ||
                    movieDetailState is UiState.Error
        }


        LaunchedEffect(movieDetailState) {
            if (movieDetailState is UiState.Success){
                val detailData = (movieDetailState as UiState.Success<MovieDetailResponse>).data
                viewModel.clearMovieDetail()
                navigateToDetail(
                    detailData.id,
                    detailData.original_title ?: detailData.original_name ?: "what if",
                    detailData.overview ?: "",
                    detailData.genres ?: listOf() ,
                    detailData.poster_path ?: "",
                    detailData.backdrop_path ?: "",
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            when {
                isLoading -> LoadingDialog(modifier)
                isError -> ErrorDialog(modifier)
                isEmpty -> EmptyDialog(modifier)
            }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                NowPlayingSection(
                    movieState,
                    modifier = modifier,
                    viewModel = viewModel
                )
                NowAiringSection(
                    state = tvState,
                    modifier = modifier,
                    viewModel = viewModel
                )
                UpcomingMovieSection(
                    state = upcomingState,
                    modifier = modifier,
                    viewModel = viewModel
                )
                // penting: biar bisa ditarik walau konten sedikit/empty
                Spacer(Modifier.height(300.dp))
            }

        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    JetMoviesAppTheme {
        HomeScreen(navigateToDetail = {_,_,_,_,_,_ ->})
    }
}

@Composable
fun NowPlayingSection(
    state:  UiState<List<ResultItem>>,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    when(state){
        is UiState.Error -> {}
        is UiState.Loading -> {}
        is UiState.Success -> {
            val data = state.data
            SectionText("In Theaters")
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(data, key =  {it.id}) { movie ->
                    MovieItem(
                        backdrop_path = movie.backdrop_path ?: "",
                        title = movie.title,
                        modifier = Modifier.clickable {
                            viewModel.movieDetail("movie",movie.id)
                        }
                    )
                }
            }
        }

        UiState.Empty -> {}
    }

}

@Composable
fun NowAiringSection(
    state:  UiState<List<TvResultItem>>,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    when(state){
        is UiState.Error -> {}
        is UiState.Loading -> {}
        is UiState.Success -> {
            val data = state.data
            SectionText("On The Air")
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(data, key =  {it.id}) { tv ->
                    MovieItem(
                        backdrop_path = tv.poster_path,
                        title = tv.original_name,
                        modifier = Modifier.clickable{
                            viewModel.movieDetail("tv", tv.id)
                        }
                    )
                }
            }
        }
        UiState.Empty -> {}
    }
}

@Composable
fun UpcomingMovieSection(
    state:  UiState<UpcomingResponses>,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    when(state){
        is UiState.Error -> {}
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
                        order = (index + 1).toString(),
                        modifier = Modifier.clickable{
                            viewModel.movieDetail("movie", upcoming.id)
                        }
                    )
                    if (index < responsesData.results.lastIndex) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
        UiState.Empty -> {}
    }
}