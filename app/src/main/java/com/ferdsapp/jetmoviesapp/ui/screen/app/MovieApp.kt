package com.ferdsapp.jetmoviesapp.ui.screen.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ferdsapp.jetmoviesapp.data.detail.movie.MovieDetailGenre
import com.ferdsapp.jetmoviesapp.ui.navigation.Screen
import com.ferdsapp.jetmoviesapp.ui.screen.components.BottomBar
import com.ferdsapp.jetmoviesapp.ui.screen.components.MovieTopAppBar
import com.ferdsapp.jetmoviesapp.ui.screen.detail.DetailScreen
import com.ferdsapp.jetmoviesapp.ui.screen.favorite.FavoriteScreen
import com.ferdsapp.jetmoviesapp.ui.screen.home.HomeScreen
import com.ferdsapp.jetmoviesapp.ui.screen.search.SearchScreen
import com.ferdsapp.jetmoviesapp.ui.theme.JetMoviesAppTheme
import com.google.gson.Gson

@Composable
fun MovieApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentState = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentState != Screen.DetailMovie.route){
                BottomBar(navController = navController)
            }
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
                    navigateToDetail = { movieId, movieTitle, movieOverview, movieGenre,moviePoster, movieBackground ->
                        navController.navigate(Screen.DetailMovie.createRoute(movieId, movieTitle, movieOverview, moviePoster, movieBackground, movieGenre))
                    }
                )
            }

            composable(Screen.Search.route){
                SearchScreen(
                    navigateToDetail = { movieId, movieTitle, movieOverview, movieGenre,moviePoster, movieBackground ->
                        navController.navigate(Screen.DetailMovie.createRoute(movieId, movieTitle, movieOverview, moviePoster, movieBackground, movieGenre))
                    }
                )
            }

            composable(Screen.Favorite.route){
                FavoriteScreen()
            }
            composable(
                route = Screen.DetailMovie.route,
                arguments = listOf(
                    navArgument("movieId") {type = NavType.IntType},
                    navArgument("movieTitle"){type = NavType.StringType},
                    navArgument("movieOverview"){type = NavType.StringType},
                    navArgument("moviePoster"){type = NavType.StringType},
                    navArgument("movieBackground"){type = NavType.StringType},
                    navArgument("movieGenre"){type = NavType.StringType}
                    )
            ){
                val id = it.arguments?.getInt("movieId") ?: -1
                val title = it.arguments?.getString("movieTitle") ?: ""
                val overview = it.arguments?.getString("movieOverview") ?: ""
                val moviePoster = it.arguments?.getString("moviePoster") ?: ""
                val movieBackground = it.arguments?.getString("movieBackground") ?: ""

                val genreJson = it.arguments?.getString("movieGenre") ?: ""
                val movieGenre = Gson().fromJson(genreJson, Array<MovieDetailGenre>::class.java).toList()

                DetailScreen(
                    id = id,
                    movieTitle = title,
                    overview = overview,
                    moviePoster = moviePoster,
                    movieBackground = movieBackground,
                    listGenre = movieGenre,
                    navigateBack = {
                        navController.navigateUp()
                    }
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

