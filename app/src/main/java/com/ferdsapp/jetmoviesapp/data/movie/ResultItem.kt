package com.ferdsapp.jetmoviesapp.data.movie

import com.google.gson.annotations.SerializedName

data class ResultItem (
    @field:SerializedName("adult")
    var adult: Boolean? = false,

    @field:SerializedName("backdrop_path")
    var backdrop_path: String? = "",

    @field:SerializedName("genre_ids")
    var genre_ids: List<Int>? = null,

    @field:SerializedName("id")
    var id: Int,

    @field:SerializedName("title")
    var title: String = "",

    @field:SerializedName("overview")
    var overview: String = ""
)