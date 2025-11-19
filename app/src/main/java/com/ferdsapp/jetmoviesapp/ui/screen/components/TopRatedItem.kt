package com.ferdsapp.jetmoviesapp.ui.screen.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ferdsapp.jetmoviesapp.R
import com.ferdsapp.jetmoviesapp.ui.theme.JetMoviesAppTheme

@Composable
fun TopRatedItem(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "#1",
        )
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(R.drawable.onepiece)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(60.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun TopRatedItemPreview() {
    JetMoviesAppTheme {
//        TopRatedItem(modifier = Modifier)
        Row(
            modifier = Modifier.padding(start = 8.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "#1",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Image(
                painter = painterResource(R.drawable.onepiece),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.height(80.dp)
                    .aspectRatio(2f/3f)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(
                verticalArrangement = Arrangement.spacedBy((-8).dp),
            ) {
                Text(
                    text = "Title",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "Author",
                    fontSize = 8.sp
                )
            }
        }
    }
}