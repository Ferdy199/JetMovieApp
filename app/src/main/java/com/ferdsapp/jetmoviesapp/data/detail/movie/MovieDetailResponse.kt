package com.ferdsapp.jetmoviesapp.data.detail.movie

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName("original_title")
    var original_title: String?,

    @SerializedName("overview")
    var overview: String?,

    @SerializedName("backdrop_path")
    var backdrop_path: String?,

    @SerializedName("genres")
    var genres: List<MovieDetailGenre>? = listOf(),

    @SerializedName("id")
    var id: Int,

    @SerializedName("poster_path")
    var poster_path: String?,

    @SerializedName("release_date")
    var release_date: String?,

    @SerializedName("runtime")
    var runtime: String?,

    @SerializedName("vote_average")
    var vote_average: String?
)