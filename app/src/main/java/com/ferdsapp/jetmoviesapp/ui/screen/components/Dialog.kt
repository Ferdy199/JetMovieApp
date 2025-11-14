package com.ferdsapp.jetmoviesapp.ui.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ferdsapp.jetmoviesapp.ui.theme.JetMoviesAppTheme

@Composable
fun LoadingDialog(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Loading get data"
        )
    }
}

@Preview
@Composable
private fun LoadingDialogPreview() {
    JetMoviesAppTheme {
        LoadingDialog()
    }
}

@Composable
fun ErrorDialog(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Failed get data"
        )
    }
}

@Preview
@Composable
private fun ErrorDialogPreview() {
    JetMoviesAppTheme {
        ErrorDialog()
    }
}