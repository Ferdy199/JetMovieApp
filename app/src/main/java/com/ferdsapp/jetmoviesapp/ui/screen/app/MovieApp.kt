package com.ferdsapp.jetmoviesapp.ui.screen.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ferdsapp.jetmoviesapp.ui.navigation.Screen
import com.ferdsapp.jetmoviesapp.ui.screen.components.BottomBar
import com.ferdsapp.jetmoviesapp.ui.screen.favorite.FavoriteScreen
import com.ferdsapp.jetmoviesapp.ui.screen.home.HomeScreen
import com.ferdsapp.jetmoviesapp.ui.screen.search.SearchScreen
import com.ferdsapp.jetmoviesapp.ui.theme.JetMoviesAppTheme

@Composable
fun MovieApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        },
        modifier = modifier,
        containerColor = Color.White,
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(Screen.Home.route) {
                HomeScreen()
            }

            composable(Screen.Search.route){
                SearchScreen()
            }

            composable(Screen.Favorite.route){
                FavoriteScreen()
            }
        }
    }
}

@Preview
@Composable
private fun MovieAppPreview() {
    JetMoviesAppTheme {
        MovieApp()
    }
}

