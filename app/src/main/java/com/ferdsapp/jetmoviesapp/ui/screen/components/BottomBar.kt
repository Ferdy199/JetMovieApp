package com.ferdsapp.jetmoviesapp.ui.screen.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.ferdsapp.jetmoviesapp.ui.navigation.NavigationItem
import com.ferdsapp.jetmoviesapp.ui.navigation.Screen
import com.ferdsapp.jetmoviesapp.ui.theme.JetMoviesAppTheme

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController
    ) {
    NavigationBar(
        modifier = modifier
    ) {
        val navigationItems = listOf(
            NavigationItem(
                title = "Home",
                image = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = "Search",
                image = Icons.Default.Search,
                screen = Screen.Search
            ),
            NavigationItem(
                title = "Favorite",
                image = Icons.Default.Favorite,
                screen = Screen.Favorite
            )
        )

        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.image,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(item.title)
                },
                selected = false,
                onClick = {
                    navController.navigate(item.screen.route){
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BottomBarPreview() {
    JetMoviesAppTheme {
        BottomBar(
            navController = NavHostController(LocalContext.current)
        )
    }
}