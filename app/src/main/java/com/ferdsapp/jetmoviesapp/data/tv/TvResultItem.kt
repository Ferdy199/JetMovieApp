package com.ferdsapp.jetmoviesapp.data.tv

import com.google.gson.annotations.SerializedName

data class TvResultItem(
    @SerializedName("poster_path")
    var poster_path: String = "",

    @SerializedName("genre_ids")
    var genre_ids: List<Int> = listOf(),

    @SerializedName("overview")
    var overview: String = "",

    @SerializedName("original_name")
    var original_name: String = ""
)