package com.ferdsapp.jetmoviesapp.ui.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ferdsapp.jetmoviesapp.data.movie.ResultItem

@Composable
fun MovieItem(
    movieItem: ResultItem? = null,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(top = 8.dp)
            .width(140.dp)
    ) {
        Card(
            modifier = modifier.width(140.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500" + movieItem?.backdrop_path,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(2f / 3f)
            )
        }

        Text(
            text = movieItem?.title ?: "-",
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieItemPreview() {
    MaterialTheme {
        MovieItem()
    }
}