package com.ferdsapp.jetmoviesapp.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ferdsapp.jetmoviesapp.data.detail.movie.MovieDetailResponse
import com.ferdsapp.jetmoviesapp.data.movie.ResultItem
import com.ferdsapp.jetmoviesapp.data.tv.TvResultItem
import com.ferdsapp.jetmoviesapp.data.upcoming.UpcomingResponses
import com.ferdsapp.jetmoviesapp.ui.screen.components.ErrorDialog
import com.ferdsapp.jetmoviesapp.ui.screen.components.LoadingDialog
import com.ferdsapp.jetmoviesapp.ui.screen.components.MovieItem
import com.ferdsapp.jetmoviesapp.ui.screen.components.SectionText
import com.ferdsapp.jetmoviesapp.ui.screen.components.UpComingItem
import com.ferdsapp.jetmoviesapp.ui.screen.state.UiState
import com.ferdsapp.jetmoviesapp.ui.theme.JetMoviesAppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (Int, String, String, String, String) -> Unit
    ) {

    val movieState by viewModel.movieUiState.collectAsStateWithLifecycle()
    val tvState by viewModel.tvUiState.collectAsStateWithLifecycle()
    val upcomingState by viewModel.upComingState.collectAsStateWithLifecycle()
    val movieDetailState by viewModel.movieDetailState.collectAsStateWithLifecycle()

    val isLoading = remember(movieState, tvState, upcomingState, movieDetailState) {
        derivedStateOf {
            movieState == UiState.Loading ||
                    tvState == UiState.Loading ||
                    upcomingState == UiState.Loading ||
                    movieDetailState == UiState.Loading
        }
    }

    Box(
        modifier = Modifier
    ) {

        if (isLoading.value){
            LoadingDialog()
        }

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            NowPlayingSection(
                movieState,
                movieDetailState = movieDetailState,
                modifier = modifier,
                navigateToDetail = navigateToDetail
            )
            NowAiringSection(
                state = tvState,
                modifier = modifier,
                tvDetailState = movieDetailState,
                navigateToDetail = navigateToDetail
            )
            UpcomingMovieSection(
                state = upcomingState,
                upComingDetailState = movieDetailState,
                navigateToDetail = navigateToDetail,
                modifier = modifier,
            )
        }

    }

}

@Preview
@Composable
private fun HomeScreenPreview() {
    JetMoviesAppTheme {
        HomeScreen(navigateToDetail = {_,_,_,_,_ ->})
    }
}

@Composable
fun NowPlayingSection(
    state:  UiState<List<ResultItem>>,
    movieDetailState: UiState<MovieDetailResponse>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int, String, String, String, String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    when(state){
        is UiState.Error -> {
            ErrorDialog(modifier)
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

        UiState.Empty -> ErrorDialog()
    }

    LaunchedEffect(movieDetailState) {
        if (movieDetailState is UiState.Success){
            val detailData = movieDetailState.data
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

@Composable
fun NowAiringSection(
    state:  UiState<List<TvResultItem>>,
    tvDetailState: UiState<MovieDetailResponse>,
    navigateToDetail: (Int, String, String, String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    var tempData: TvResultItem? = null
    when(state){
        is UiState.Error -> {
            ErrorDialog(modifier)
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
                    tempData = tv
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
        UiState.Empty -> ErrorDialog()
    }

    LaunchedEffect(tvDetailState) {
        if (tvDetailState is UiState.Success){
            val detailData = tvDetailState.data
            viewModel.clearMovieDetail()
            navigateToDetail(
                detailData.id,
                detailData.original_title ?: tempData?.original_name ?: "",
                detailData.overview ?: "",
                detailData.poster_path ?: "",
                detailData.backdrop_path ?: ""
            )
        }
    }
}

@Composable
fun UpcomingMovieSection(
    state:  UiState<UpcomingResponses>,
    upComingDetailState: UiState<MovieDetailResponse>,
    navigateToDetail: (Int, String, String, String, String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
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
        UiState.Empty -> ErrorDialog()
    }

    LaunchedEffect(upComingDetailState) {
        if (upComingDetailState is UiState.Success){
            val detailData = upComingDetailState.data
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