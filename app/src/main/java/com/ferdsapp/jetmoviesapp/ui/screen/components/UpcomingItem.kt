package com.ferdsapp.jetmoviesapp.ui.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ferdsapp.jetmoviesapp.data.upcoming.ListDates
import com.ferdsapp.jetmoviesapp.data.upcoming.UpcomingResults
import com.ferdsapp.jetmoviesapp.ui.theme.JetMoviesAppTheme

@Composable
fun UpComingItem(
    releasedDate: ListDates,
    upcomingResults: UpcomingResults,
    order: String,
    modifier: Modifier = Modifier,
    ) {
    Row(
        modifier = modifier.padding(start = 8.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = "#$order",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500" + upcomingResults.poster_path,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(140.dp)
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(8.dp))
                .padding(start = 4.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(start = 12.dp)
        ) {
            Text(
                text = upcomingResults.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 3,
                overflow = TextOverflow.Clip
            )
            Text(
                text = releasedDate.minimum,
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UpComingItemPreview() {
    JetMoviesAppTheme {
        UpComingItem(
            releasedDate = ListDates(
                "Released Data 2",
                "Released Data 1"
            ),
            UpcomingResults(
                "",
                "Zootopia 2",
            ),
            "3",
        )
    }
}