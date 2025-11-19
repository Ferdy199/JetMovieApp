package com.ferdsapp.jetmoviesapp.ui.screen.search

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices.PIXEL_3
import androidx.compose.ui.tooling.preview.Preview
import com.ferdsapp.jetmoviesapp.ui.screen.components.SearchBarApp
import com.ferdsapp.jetmoviesapp.ui.theme.JetMoviesAppTheme

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier,
    ) {
        SearchBarApp()
    }
}

@Preview(showBackground = true, showSystemUi = true, device = PIXEL_3)
@Composable
private fun SearchScreenPreview() {
    JetMoviesAppTheme {
        SearchScreen()
    }
}