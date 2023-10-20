package com.andersen.rick_and_morty.domain.model

data class Location(
    val id: Int,
    val name: String?,
    val type: String?,
    val dimension: String?,
    val residents: List<String>?,
)