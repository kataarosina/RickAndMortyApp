package com.andersen.rick_and_morty.data.repository

import com.andersen.rick_and_morty.data.database.RickAndMortyDatabase
import com.andersen.rick_and_morty.data.mapper.toDomainModels
import com.andersen.rick_and_morty.domain.model.Character
import com.andersen.rick_and_morty.domain.model.ChatacterFilters
import com.andersen.rick_and_morty.domain.model.Episode
import com.andersen.rick_and_morty.domain.repository.CharacterLocalRepository

internal class CharacterLocalRepositoryImpl(
    private val characterDatabase: RickAndMortyDatabase,

    ) : CharacterLocalRepository {
    override suspend fun getCharacterFromDao(id: Int): Character {
        return characterDatabase.characterDao().getCharacter(id).toDomainModels()
    }

    override suspend fun insertCharacters(characters: List<Character>) {
        characterDatabase.characterDao().insert(characters.map { it.toDomainModels() })
    }

    override suspend fun getCharactersFromDao(filter: ChatacterFilters): List<Character> {
        return characterDatabase.characterDao()
            .getCharacters(filter.species, filter.status, filter.gender).map {
            it.toDomainModels()
        }
    }


    override suspend fun getCharacterEpisodesFromDao(listIds: List<Int>): List<Episode> {
        return characterDatabase.episodeDao().getEpisodes(listIds).map {
            it.toDomainModels()
        }
    }

}