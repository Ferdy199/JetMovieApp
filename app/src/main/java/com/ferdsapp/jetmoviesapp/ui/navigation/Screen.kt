package com.ferdsapp.jetmoviesapp.ui.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    data object Home: Screen("home")
    data object Search: Screen("search")
    data object Favorite: Screen("favorite")
    data object DetailMovie: Screen("home/{movieId}/{movieTitle}/{movieOverview}/{moviePoster}/{movieBackground}"){
        fun createRoute(
            movieId: Int,
            movieTitle: String,
            movieOverview: String,
            moviePoster: String,
            movieBackground: String
        ) = "home/$movieId/$movieTitle/$movieOverview/${Uri.encode(moviePoster)}/${Uri.encode(movieBackground)}"
    }
}