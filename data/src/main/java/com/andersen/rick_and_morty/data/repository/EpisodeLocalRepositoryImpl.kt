package com.andersen.rick_and_morty.data.repository

import com.andersen.rick_and_morty.data.database.RickAndMortyDatabase
import com.andersen.rick_and_morty.data.mapper.toDomainModels
import com.andersen.rick_and_morty.domain.model.Character
import com.andersen.rick_and_morty.domain.model.Episode
import com.andersen.rick_and_morty.domain.model.EpisodeFilters
import com.andersen.rick_and_morty.domain.repository.EpisodeLocalRepository

internal class EpisodeLocalRepositoryImpl(
    private val episodeDatabase: RickAndMortyDatabase,

    ) : EpisodeLocalRepository {
    override suspend fun getEpisodeFromDao(id: Int): Episode {
        return episodeDatabase.episodeDao().getEpisode(id).toDomainModels()
    }

    override suspend fun insertEpisodes(episodes: List<Episode>) {
        episodeDatabase.episodeDao().insert(episodes.map { it.toDomainModels() })
    }

    override suspend fun getEpisodesFromDao(filters: EpisodeFilters): List<Episode> {
        return episodeDatabase.episodeDao().getEpisodes(filters.episode, filters.airDate)
            .map { it.toDomainModels() }
    }

    override suspend fun getEpisodeCharactersFromDao(listIds: List<Int>): List<Character> {
        return episodeDatabase.characterDao().getCharactersByListId(listIds)
            .map { it.toDomainModels() }
    }

}

