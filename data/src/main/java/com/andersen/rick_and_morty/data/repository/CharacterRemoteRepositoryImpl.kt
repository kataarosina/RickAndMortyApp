package com.andersen.rick_and_morty.data.repository

import com.andersen.rick_and_morty.data.api.RickandMortyApi
import com.andersen.rick_and_morty.data.mapper.toDomainModels
import com.andersen.rick_and_morty.domain.repository.CharacterRemoteRepository
import com.andersen.rick_and_morty.domain.model.Character
import com.andersen.rick_and_morty.domain.model.Episode

internal class CharacterRemoteRepositoryImpl(
    private val characterService: RickandMortyApi,
) : CharacterRemoteRepository {

    override suspend fun getCharacter(id: Int): Result<Character> {
        return runCatching {
            characterService.getCharacter(id)

        }.map { it.toDomainModels() }
    }


    override suspend fun getCharacters(
        page: Int,
        pageSize: Int,
        species: String,
        type: String,
        status: String,
        gender: String,
    ): Result<List<Character>> {
        return runCatching {

            characterService.getCharacters(page, pageSize, species, type, status, gender).results
        }.map {
            it.toDomainModels()
        }
    }

    override suspend fun getEpisodes(episodeUrls: List<Int>): Result<List<Episode>> {
        return runCatching {
            val episodes = mutableListOf<Episode>()
            episodeUrls.forEach { episodeId ->
                val episode = characterService.getEpisode(episodeId)
                episodes.add(episode.toDomainModels())
            }
            episodes
        }
    }
}