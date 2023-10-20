package com.andersen.rick_and_morty.data.model

internal data class CharactersDTO(
    val results: List<CharacterDTO>,
)

internal data class CharacterDTO(
    val id: Int,
    val name: String?,
    val image: String?,
    val species: String?,
    val type: String?,
    val status: String?,
    val gender: String?,
    val location: CharacterLocation?,
    val origin: CharacterLocation?,
    val episode: List<String>?
)

internal data class CharacterLocation(
    val name: String, val url: String
)