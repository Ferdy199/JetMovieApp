package com.ferdsapp.jetmoviesapp.data.search

import com.google.gson.annotations.SerializedName

data class SearchResponsesItem(
    @field:SerializedName("adult")
    var adult: Boolean = false,

    @field:SerializedName("backdrop_path")
    var backdrop_path: String = "",

    @field:SerializedName("id")
    var id: Int = 0,

    @field:SerializedName("original_title")
    var original_title: String = "",

    @field:SerializedName("poster_path")
    var poster_path: String = "",

    @field:SerializedName("media_type")
    var media_type: String = "",

    @field:SerializedName("genre_ids")
    var genre_ids: List<Int> = listOf()
)