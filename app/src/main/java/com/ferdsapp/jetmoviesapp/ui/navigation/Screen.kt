package com.ferdsapp.jetmoviesapp.ui.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("home")
    data object Search: Screen("search")
    data object Favorite: Screen("favorite")
    data object DetailMovie: Screen("home/{movieId}"){
        fun createRoute(movieId: Int) = "home/$movieId"
    }
}