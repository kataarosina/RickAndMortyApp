package com.andersen.rick_and_morty.domain.model

data class Character(
    val id: Int,
    val name: String?,
    val image: String?,
    val species: String?,
    val type: String?,
    val status: String?,
    val gender: String?,
    val episode: List<String>?,
    val location: String?,
    val origin: String?,

    )