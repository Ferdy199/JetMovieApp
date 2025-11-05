package com.ferdsapp.jetmoviesapp.data.movie

import com.google.gson.annotations.SerializedName

data class MovieNowPlayingResponses(
    @field:SerializedName("page")
    var page: Int = 0,

    @field:SerializedName("results")
    var results: List<ResultItem>
)