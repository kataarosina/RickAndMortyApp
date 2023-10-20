package com.andersen.rick_and_morty.data.repository

import com.andersen.rick_and_morty.data.api.RickandMortyApi
import com.andersen.rick_and_morty.data.mapper.toDomainModels
import com.andersen.rick_and_morty.domain.model.Character
import com.andersen.rick_and_morty.domain.repository.EpisodeRemoteRepository
import com.andersen.rick_and_morty.domain.model.Episode

internal class EpisodeRemoteRepositoryImpl(
    private val episodeService: RickandMortyApi,
) : EpisodeRemoteRepository {

    override suspend fun getEpisode(id: Int): Result<Episode> {
        return runCatching {
            episodeService.getEpisode(id)
        }.map { it.toDomainModels() }
    }

    override suspend fun getEpisodes(
        page: Int, pageSize: Int, episode: String, airDate: String
    ): Result<List<Episode>> {
        return kotlin.runCatching {
            episodeService.getEpisodes(page, pageSize, episode, airDate).results
        }.map {
            it.toDomainModels()
        }
    }

    override suspend fun getCharacters(characterUrls: List<Int>): Result<List<Character>> {
        return runCatching {
            val characters = mutableListOf<Character>()
            characterUrls.forEach { characterId ->
                val character = episodeService.getCharacter(characterId)
                characters.add(character.toDomainModels())
            }
            characters
        }
    }

}