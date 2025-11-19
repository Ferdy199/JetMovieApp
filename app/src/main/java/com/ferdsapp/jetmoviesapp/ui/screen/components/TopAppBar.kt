package com.ferdsapp.jetmoviesapp.ui.screen.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.ferdsapp.jetmoviesapp.ui.theme.JetMoviesAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text( text = "JetMovieApp",
                modifier = modifier,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
                )
        }
    )
}

@Preview
@Composable
private fun TopAppBarPreview() {
    JetMoviesAppTheme {
        MovieTopAppBar()
    }
}