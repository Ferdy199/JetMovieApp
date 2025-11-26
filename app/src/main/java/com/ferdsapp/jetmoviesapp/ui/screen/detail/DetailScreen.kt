package com.ferdsapp.jetmoviesapp.ui.screen.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ferdsapp.jetmoviesapp.ui.theme.JetMoviesAppTheme

@Composable
fun DetailScreen(
    movieId: Int,
    movieTitle: String,
    overview: String,
    moviePoster: String,
    movieBackground: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Detail Screen $movieId $movieTitle $overview $moviePoster $movieBackground"
        )
    }
}

@Preview
@Composable
private fun DetailScreenPreview() {
    JetMoviesAppTheme {
        DetailScreen(movieId = 0, "", "", "", "", {})
    }
}