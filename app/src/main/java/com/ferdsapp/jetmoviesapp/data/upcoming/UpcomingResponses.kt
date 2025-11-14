package com.ferdsapp.jetmoviesapp.data.upcoming

import com.google.gson.annotations.SerializedName

data class UpcomingResponses(
    @field:SerializedName("dates")
    val dates: ListDates,

    @field:SerializedName("page")
    val page: Int = 0,

    @field:SerializedName("results")
    val results: List<UpcomingResults>,

    @field:SerializedName("total_pages")
    val total_pages: Int = 0
)