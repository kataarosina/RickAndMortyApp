package com.andersen.rick_and_morty.data.mapper

import com.andersen.rick_and_morty.data.model.CharacterDTO
import com.andersen.rick_and_morty.data.model.CharacterEntity
import com.andersen.rick_and_morty.domain.model.Character

internal fun List<CharacterDTO>.toDomainModels(): List<Character> {
    return map { it.toDomainModels() }
}


internal fun CharacterDTO.toDomainModels(): Character {
    return Character(
        id = id,
        name = name,
        image = image,
        species = species,
        type = type,
        status = status,
        gender = gender,
        episode = episode,
        location = location?.url,
        origin = origin?.url
    )
}


internal fun Character.toDomainModels(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        image = image,
        species = species,
        type = type,
        status = status,
        gender = gender,
        location = location,
        origin = origin,
        episodes = episode
    )
}


internal fun CharacterEntity.toDomainModels(): Character {
    return Character(
        id = id,
        name = name,
        image = image,
        species = species,
        type = type,
        status = status,
        gender = gender,
        episode = episodes,
        location = location,
        origin = origin,
    )
}