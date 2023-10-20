package com.andersen.rick_and_morty.data.mapper

import com.andersen.rick_and_morty.data.model.EpisodeDTO
import com.andersen.rick_and_morty.data.model.EpisodeEntity
import com.andersen.rick_and_morty.domain.model.Episode


internal fun List<EpisodeDTO>.toDomainModels(): List<Episode> {
    return map { it.toDomainModels() }
}


internal fun EpisodeDTO.toDomainModels(): Episode {
    return Episode(
        id = id,
        name = name,
        episode = episode,
        airDate = airDate,
        characters = characters,
    )
}


internal fun Episode.toDomainModels(): EpisodeEntity {
    return EpisodeEntity(
        id = id, name = name, episode = episode, airDate = airDate, characters = characters

    )
}


internal fun EpisodeEntity.toDomainModels(): Episode {
    return Episode(
        id = id, name = name, episode = episode, airDate = airDate, characters = characters
    )
}