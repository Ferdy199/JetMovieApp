package com.ferdsapp.jetmoviesapp.ui.navigation

import android.net.Uri
import com.ferdsapp.jetmoviesapp.data.detail.movie.MovieDetailGenre
import com.google.gson.Gson

sealed class Screen(val route: String) {
    data object Home: Screen("home")
    data object Search: Screen("search")
    data object Favorite: Screen("favorite")
    data object DetailMovie: Screen("home/{movieId}/{movieTitle}/{movieOverview}/{movieGenre}/{moviePoster}/{movieBackground}"){
        fun createRoute(
            movieId: Int,
            movieTitle: String,
            movieOverview: String,
            moviePoster: String,
            movieBackground: String,
            movieGenre: List<MovieDetailGenre>
        ) = "home/$movieId/$movieTitle/${Uri.encode(movieOverview)}/${Uri.encode(Gson().toJson(movieGenre))}/${Uri.encode(moviePoster)}/${Uri.encode(movieBackground)}"
    }
}