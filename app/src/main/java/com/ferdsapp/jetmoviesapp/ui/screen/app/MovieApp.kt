package com.ferdsapp.jetmoviesapp.ui.screen.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ferdsapp.jetmoviesapp.ui.navigation.Screen
import com.ferdsapp.jetmoviesapp.ui.screen.components.BottomBar
import com.ferdsapp.jetmoviesapp.ui.screen.components.MovieTopAppBar
import com.ferdsapp.jetmoviesapp.ui.screen.detail.DetailScreen
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
        topBar = {
            MovieTopAppBar()
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
                HomeScreen(
                    navigateToDetail = { movieId ->
                        navController.navigate(Screen.DetailMovie.createRoute(movieId))
                    }
                )
            }

            composable(Screen.Search.route){
                SearchScreen()
            }

            composable(Screen.Favorite.route){
                FavoriteScreen()
            }
            composable(
                route = Screen.DetailMovie.route,
                arguments = listOf(
                    navArgument("movieId") {type = NavType.IntType},
                    navArgument("movieOverview"){type = NavType.StringType},
                    navArgument("movieTitle"){type = NavType.StringType},
                    navArgument("moviePoster"){type = NavType.StringType},
                    navArgument("movieBackground"){type = NavType.StringType}
                    )
            ){
                val id = it.arguments?.getInt("movieId") ?: -1
                DetailScreen(
                    movieId = id
                )
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

