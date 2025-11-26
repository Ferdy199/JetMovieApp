package com.ferdsapp.jetmoviesapp.data.detail.movie

import com.google.gson.annotations.SerializedName

data class MovieDetailGenre(
    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String
)