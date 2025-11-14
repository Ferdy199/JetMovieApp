package com.ferdsapp.jetmoviesapp.data.upcoming

import com.google.gson.annotations.SerializedName

data class UpcomingResults(
    @field:SerializedName("poster_path")
    var poster_path: String = "",

    @field:SerializedName("title")
    var title: String = "",

    @field:SerializedName("id")
    var id: Int = 0
)