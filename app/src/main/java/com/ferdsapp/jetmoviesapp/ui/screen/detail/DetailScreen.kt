package com.ferdsapp.jetmoviesapp.ui.screen.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ferdsapp.jetmoviesapp.R
import com.ferdsapp.jetmoviesapp.data.detail.movie.MovieDetailGenre
import com.ferdsapp.jetmoviesapp.data.favorite.FavoriteMovie
import com.ferdsapp.jetmoviesapp.ui.screen.components.GenreItem
import com.ferdsapp.jetmoviesapp.ui.screen.state.UiState
import com.ferdsapp.jetmoviesapp.ui.theme.JetMoviesAppTheme
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    id: Int,
    movieTitle: String,
    overview: String,
    moviePoster: String,
    movieBackground: String,
    listGenre: List<MovieDetailGenre>,
    navigateBack: () -> Unit,
    detailViewModel: DetailViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val state = detailViewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                detailViewModel.addFavoriteMovie(
                    FavoriteMovie(
                        id,
                        movieTitle,
                        moviePoster
                    )
                )
                state.value.let { uiState ->
                    when(uiState){
                        is UiState.Empty -> {}
                        is UiState.Error -> {}
                        is UiState.Loading -> {}
                        is UiState.Success -> {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Berhasil menambahkan ke Favorite",
                                    duration = SnackbarDuration.Short,
                                )
                            }
                        }
                    }
                }

            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        },
        snackbarHost = {
           SnackbarHost(snackbarHostState)
        }
    ) {
        DetailScreenContent(
            id,
            movieTitle,
            overview,
            moviePoster,
            movieBackground,
            listGenre,
            navigateBack,
            modifier
        )
    }
}

@Composable
fun DetailScreenContent(
    id: Int,
    movieTitle: String,
    overview: String,
    moviePoster: String,
    movieBackground: String,
    listGenre: List<MovieDetailGenre>,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movieBackground}",
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.noimage),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 8.dp),

            ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${moviePoster}",
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.noimage),
                contentDescription = null,
                modifier = Modifier
                    .heightIn(min = 180.dp, max = 240.dp)
                    .aspectRatio(2f / 3f)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = movieTitle,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = FontFamily.SansSerif,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(80.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.heightIn(max = 160.dp)
                ) {
                    items(listGenre, key = {it.id}){ genreList ->
                        GenreItem(genreList.name)
                    }
                }
            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 8.dp, bottom = 16.dp)
        ) {
            Text(
                text = "Overview",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = overview,
                fontSize = 16.sp,
                fontWeight = FontWeight.W200,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Justify,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailScreenPreview() {
    JetMoviesAppTheme {
        DetailScreen(
            1,
            "Panda",
            "Relaxed pacifist, matcha addict, and convinced vegan, Panda runs a small shack in a remote corner of the Camargue. No phone, no computer, no car; he lives with his 16-year-old in this perfect cocoon of peace and serenity, avoiding anything resembling conflict. How to imagine that this wise man in flip-flops and a faded t-shirt was once a cop? And not just any cop. One of the best. Unfortunately, even in paradise, there's no way to be completely peaceful. When his former life comes knocking at the door, Panda finds himself obliged to return to duty... But in his own way. Without weapons or violence and not too early in the morning. Zen, you know.",
            "",
            "",
            listGenre = listOf(
                MovieDetailGenre(1, "Fiction"),
                MovieDetailGenre(2, "romance"),
                MovieDetailGenre(3, "War")
            ),
            {}
        )
    }
}