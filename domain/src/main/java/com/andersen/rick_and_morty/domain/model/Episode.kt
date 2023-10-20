package com.andersen.rick_and_morty.domain.model

data class Episode(
    val id: Int,
    val name: String?,
    val episode: String?,
    val airDate: String?,
    val characters: List<String>?,
)