package com.ferdsapp.jetmoviesapp.data.upcoming

import com.google.gson.annotations.SerializedName

data class ListDates(
    @field:SerializedName("maximum")
    val maximum: String = "",

    @field:SerializedName("minimum")
    val minimum: String = ""
)