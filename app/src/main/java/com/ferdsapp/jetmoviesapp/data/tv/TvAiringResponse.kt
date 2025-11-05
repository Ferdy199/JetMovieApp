package com.ferdsapp.jetmoviesapp.data.tv

data class TvAiringResponse (
    var page: Int = 0 ,
    var results: List<TvResultItem> = listOf()
)