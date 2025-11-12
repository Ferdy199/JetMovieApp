package com.ferdsapp.jetmoviesapp.ui.screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.InputField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ferdsapp.jetmoviesapp.ui.theme.JetMoviesAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarApp(modifier: Modifier = Modifier) {
    SearchBar(
        inputField = {
            InputField(
                query = "",
                onQueryChange = {},
                onSearch = {},
                expanded = false,
                onExpandedChange = {},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                placeholder = {
                    Text("Find Your Favorite Here")
                }
            )
        },
        expanded = false,
        onExpandedChange = {},
        shape = ShapeDefaults.Large,
        colors = SearchBarDefaults.colors(containerColor = Color.White),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .padding(horizontal = 16.dp)
    ) { }
}

@Preview
@Composable
private fun SearchBarAppPreview() {
    JetMoviesAppTheme {
        SearchBarApp()
    }
}