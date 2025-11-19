package com.ferdsapp.jetmoviesapp.data.search

import com.google.gson.annotations.SerializedName

data class SearchResponses(
    @field:SerializedName("page")
    var page: Int = 0,

    @field:SerializedName("results")
    var results: List<SearchResponsesItem> = listOf()
)