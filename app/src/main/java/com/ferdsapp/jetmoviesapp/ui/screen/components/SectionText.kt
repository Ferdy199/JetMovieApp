package com.ferdsapp.jetmoviesapp.ui.screen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SectionText(
    title: String,
    modifier: Modifier = Modifier,
    ) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall.copy(
            fontWeight = FontWeight.ExtraBold
        ),
        fontSize = 18.sp,
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}